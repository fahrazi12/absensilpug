package com.ivallavifahrazi.lpugabsensi.email.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String penerima, String judulEmail, String bodyEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(penerima);
        message.setSubject(judulEmail);
        message.setText(bodyEmail);

        mailSender.send(message);
    }
}
