package com.cosc300.suicidal.emailConfig;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class EmailSender {
    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String receiver, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(receiver);
            mimeMessageHelper.setFrom("thepeloDev066@gmail.com");
            mimeMessageHelper.setSubject("Confirm email");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
