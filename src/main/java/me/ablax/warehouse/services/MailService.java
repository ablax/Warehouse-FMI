package me.ablax.warehouse.services;

import me.ablax.warehouse.models.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final String sender;

    public MailService(final JavaMailSender javaMailSender, @Value("${spring.mail.username}") final String sender) {
        this.javaMailSender = javaMailSender;
        this.sender = sender;
    }

    public void sendEmail(final String email, final String token, final String name) {
        final EmailDetails details = new EmailDetails();
        details.setRecipient(email);
        details.setSubject("Reset Password");
        details.setMsgBody("Hello " + name + ",\n" +
                "\n" +
                "You have requested to reset your email.\n" +
                "To do so please access the following url:\n" +
                "https://ablax.me/reset/" + token + "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Best wishes,\n" +
                "ablax.me");

        sendSimpleMail(details);
    }

    public String sendSimpleMail(EmailDetails details) {
        try {
            final SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

}