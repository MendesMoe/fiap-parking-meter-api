package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.enums.PaymentMethod;
import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.service.SessionService;
import com.postech.parquimetro.service.TimeCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TimeCalculatorService timeService;

    @GetMapping
    @Operation(summary = "Get the sessions list", responses = {
            @ApiResponse(description = "List of sessions", responseCode = "200")
    })
    public List<ParkingSession> getAll(){
        return this.sessionService.getAll();
    }

    @PostMapping
    @Operation(summary = "Create a new session for a customer and a vehicle", responses = {
            @ApiResponse(description = "The session has been created", responseCode = "200")
    })
    public ResponseEntity create(@RequestBody ParkingSession parkingSession) throws ValidationException {

        if (parkingSession.getSessionType() == SessionType.FREE_TIME && parkingSession.getPaymentMethod() == PaymentMethod.PIX) {
            return ResponseEntity.ok("ERROR"); // TODO return a exception because this payment method is not compatible with this session type
        }

        ParkingSession session = this.sessionService.create(parkingSession);
        ParkingSessionDTO sessionDTO = session.convertToDTO();

        // Se for fixo, ele calcula os 15 minutos com base no endSession e programa o envio do email 15 minutos antes
        if (session.getSessionType() == SessionType.FIXED_TIME && session.getEndSession() != null) {

            //calcula menos 15 minutos antes do fim. Se a sessionEnd é 12h00, a gente vê que horas sao (por exemplo 11h10), calcula 15 minutos antes de 12h00 (11h45) e subtrai. 11h45 - 11h10 = 35 minutos e transforma em millisegundos
            long delay = this.timeService.get15MinBeforeExpiration(sessionDTO);
            this.sendDelayedMessage(sessionDTO, (int) delay);
        }

        // Se for free, ele calcula +45 minutos a partir da hora de criacao e programa um envio para esta hora
        if (session.getSessionType() == SessionType.FREE_TIME && session.getEndSession() == null) {
            long delay = this.timeService.get45MinInMilliseconds(sessionDTO);
            this.sendDelayedMessage(sessionDTO, (int) delay);
        }
        System.out.println("Mensagem enviada para rabbitMQ de type -----> " + session.getSessionType() + " com o SessionDTO -----> " + sessionDTO);

        return ResponseEntity.ok("session created");
    }

    public void sendDelayedMessage(ParkingSessionDTO messageSession, int delay) {
        rabbitTemplate.convertAndSend("myDelayedExchange", "myRoutingKey", messageSession, message -> {
            message.getMessageProperties().setDelay(delay);
            return message;
        });
    }

    @GetMapping("/{customerID}")
    @Operation(summary = "Get all sessions for a customer", responses = {
            @ApiResponse(description = "List of sessions", responseCode = "200")
    })
    public List<ParkingSession> getByCustomer(@PathVariable String customerID) {
        return this.sessionService.getByCustomer(customerID);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete one session by ID", responses = {
            @ApiResponse(description = "The session was deleted", responseCode = "200")
    })
    public ResponseEntity deleteById(@PathVariable String id){
        this.sessionService.deleteById(id);
        return ResponseEntity.ok("delete ok");
    }

    @PutMapping
    @Operation(summary = "Update one session", responses = {
            @ApiResponse(description = "The session was updated", responseCode = "200")
    })
    public ResponseEntity update(@RequestBody ParkingSession updateSession){
        this.sessionService.update(updateSession);
        return ResponseEntity.ok("updated session");
    }

    @GetMapping("/{sessionID}")
    @Operation(summary = "End a session", responses = {
            @ApiResponse(description = "The session was closed", responseCode = "200")
    })
    public ResponseEntity endSession(@PathVariable String sessionID) {
        ParkingSession parkingSession = this.sessionService.getById(sessionID);
        LocalDateTime now = LocalDateTime.now();
        parkingSession.setEndSession(now);
        parkingSession.setStatus(0);

        ParkingSessionDTO sessionDTO = parkingSession.convertToDTO();

        // TODO chama o serviço de envio de email para calcular o valor de fatura e enviar o recibo

        return ResponseEntity.ok("session closed");
    }
}
