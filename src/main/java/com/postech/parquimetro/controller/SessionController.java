package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.enums.SessionType;
import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.domain.session.ParkingSessionDTO;
import com.postech.parquimetro.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        ParkingSession session = this.sessionService.create(parkingSession);
        ParkingSessionDTO sessionDTO = session.convertToDTO();

        if (session.getSessionType() == SessionType.FIXED_TIME && session.getEndSession() != null) {
            //this.sendDelayedMessage(sessionDTO, 2000); definir o delay de acordo com o endSession se fixed ou com a proxima hora de FREE_TIME
        }

        //calcula menos 15 minutos antes do fim. Se a sessionEnd é 12h00, a gente vê que horas sao (por exemplo 11h10), calcula 15 minutos antes de 12h00 (11h45) e subtrai. 11h45 - 11h10 = 35 minutos e transforma em millisegundos

        this.sendDelayedMessage(sessionDTO, 20000);

        System.out.println("mensagem enviada, esperar 20 segundos e ela aparecera aqui => ");

        // Agora aqui tem que chamar o serviço de envio de email passando o dto com as informacoes da sessao

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
}
