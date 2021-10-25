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

            var id1 = execution.getVariable("email-adress").toString();
            var id2 = execution.getVariable("email-text").toString();
            final String username = "balkanabubakirov@gmail.com";
            final String password = "PisaPopa228";

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

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
                        InternetAddress.parse(id1)
                );
                message.setSubject("Тестовое сообщение");
                message.setText(id2);

                Transport.send(message);

                System.out.println("Сообщение отправлено!");

            } catch (MessagingException e) {
                e.printStackTrace();
                System.out.println("Сообщение отправлено!");
            }
    }
}

