package com.postech.parquimetro.controller;

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

    @Autowired
    private TaskScheduler taskScheduler;

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
    public ResponseEntity create(@RequestBody ParkingSession parkingSession){
        ParkingSession session = this.sessionService.create(parkingSession);
        ParkingSessionDTO sessionDTO = session.convertToDTO();

        /////// Envio para o RabbitMQ para poder ser consumido por um outro micro servico ou servico nesta aplicacao, no caso, a de noificacao ///////
        //Message message = new Message(("New parking session id: " + session.getId() + " type: " + session.getSessionType()).getBytes());
        //Podemos enviar em bytes par default, mas é melhor usar o construtor que faz o convertAndSend para enviar um json e assim poder enviar a entidade ou melhor, um DTO
        rabbitTemplate.convertAndSend("estacionamento.criado", sessionDTO);

        // nous avons besoin de l'heure de fin choisi par le customer dans la table session, si le type est FIXED
        return ResponseEntity.ok("session created");
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
