package com.example.authwalletms.service.impl;

import com.example.authwalletms.dto.request.UserDto;
import com.example.authwalletms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendOTPEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javidarkishiev@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);

        emailSender.send(message);
    }

    @KafkaListener(topics = "user-register", groupId = "myGroup")
    @Async
    public void consumeUserRegistration(UserDto user) {
        sendOTPEmail(user.email(), user.otp());
    }

}
