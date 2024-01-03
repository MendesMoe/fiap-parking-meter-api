package com.postech.parquimetro.service;

import org.springframework.stereotype.Service;

@Service
public interface ParkingNotifications {

    public String createANotification();

    public String sendANotification();
}
