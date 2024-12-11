package org.example.duan.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStorage = new HashMap<>();

    // Tạo số OTP
    public String generateAndStoreOTP(String email) {
        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(900000)); // 6 chữ số
        otpStorage.put(email, otp);
        return otp;
    }

    // Kiểm tra OTP
    public boolean validateOTP(String email, String otp) {
        return otp.equals(otpStorage.get(email));
    }

    // Gửi email
    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }
}

