package com.postech.parquimetro.service.impl;

import com.postech.parquimetro.service.ParkingNotifications;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class ParkingNotificationImpl implements ParkingNotifications {
    @Autowired
    private RabbitListener rabbitListener;

    @Override
    public String createANotification() {
        return null;
    }

    @Override
    public String sendANotification() {
        return null;
    }
}
