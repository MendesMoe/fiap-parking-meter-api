package com.postech.parquimetro.controller;

import com.postech.parquimetro.domain.session.ParkingSession;
import com.postech.parquimetro.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public List<ParkingSession> getAll(){
        return this.sessionService.getAll();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody ParkingSession parkingSession){
        this.sessionService.create(parkingSession);
        return ResponseEntity.ok("session created");
    }

    @GetMapping("/{customerID}")
    public List<ParkingSession> getByCustomer(@PathVariable String customerID) {
        return this.sessionService.getByCustomer(customerID);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        this.sessionService.deleteById(id);
        return ResponseEntity.ok("delete ok");
    }

    @PutMapping
    public ResponseEntity update(@RequestBody ParkingSession updateSession){
        this.sessionService.update(updateSession);
        return ResponseEntity.ok("updated session");
    }
}
