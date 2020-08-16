package ua.masterjoi.sundial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//Сервис для отправки писем пользователям
@Service
public class NotificationService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String message) {
        //Создали новое письмо
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        //Заполняем поля пришедшими данными
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        //Отправляем
        mailSender.send(mailMessage);
    }
}
