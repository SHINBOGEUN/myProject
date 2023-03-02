package com.example.project.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateOrderNumber {
    public static String createOrderNumber(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        String formatedNow = now.format(formatter);
        int randomNumber = (int) (Math.random() * 1000);
        return formatedNow + randomNumber;
    }
}
