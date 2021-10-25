package com.example.workflow;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TestService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

            var email = execution.getVariable("email-adress").toString();
            var text = execution.getVariable("email-text").toString();

            final String username = "balkanabubakirov@gmail.com";
            final String password = "PisaPopa228";

            Properties prop = new Properties();

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("balkanabubakirov@gmail.com"));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(email)
                );
                message.setSubject("Тестовое сообщение");
                message.setText(text);
                Transport.send(message);
                System.out.println("Сообщение отправлено!");

            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Сообщение не отправлено!");
            }
    }
}

