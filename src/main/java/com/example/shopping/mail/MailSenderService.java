package com.example.shopping.mail;

import com.example.shopping.exp.MethodNotAllowedException;
import com.example.shopping.util.JwtUtil;
import com.example.shopping.util.SpringSecurityUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class MailSenderService {
    @Autowired
    private  JavaMailSender javaMailSender;
    @Autowired
    private  EmailHistoryRepository emailHistoryRepository;
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Value("${server.host}")
    private String serverHost;
    @Value("${email.limit}")
    private Integer emailLimit;
    @Value("${email.limit.time}")
    private Integer emailLimitTime;
    public void sendRegistrationEmail(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Registration verification.\n");
        stringBuilder.append("Click to below link to complete registration\n");
        stringBuilder.append("Link: ");
        stringBuilder.append(serverHost).append("/public/auth/verification/");
        stringBuilder.append(JwtUtil.encode(toAccount));
        sendEmail(toAccount, "Registration", stringBuilder.toString());
    }
    void sendEmail(String toAccount, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
        //add email history
        addMailHistory(text, toAccount);
    }
    private void addMailHistory(String text, String toAccount) {
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setMessage(text);
        entity.setEmail(toAccount);
        entity.setCreatedDate(LocalDateTime.now());
        emailHistoryRepository.save(entity);
    }
    public void sendRegistrationEmailMime(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h1 style=\"text-align: center\">Registration verification</h1>");
        stringBuilder.append("<br><br>");
        // <p><a href="asd.dasdad.asdaasda">Click to the link to complete registration</a></p>
        stringBuilder.append("<p><a href=\"");
        stringBuilder.append(serverHost).append("/api/v1/auth/email/verification/");
        stringBuilder.append(JwtUtil.encode(toAccount)).append("\">");
        stringBuilder.append("Click to the link to complete registration</a></p>");
        sendEmailMime(toAccount, "Registration", stringBuilder.toString());
    }

    private void sendEmailMime(String toAccount, String subject, String text) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
            // add email history
            addMailHistory(text, toAccount);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkLimit(String email) {
        int count = emailHistoryRepository.countByEmailAndCreatedDateAfter(email, LocalDateTime.now().minus(emailLimitTime, ChronoUnit.SECONDS));
        if (count >= emailLimit) {
            throw new MethodNotAllowedException("Many attempts in the given time. Try again after one");
        }
    }
    public void sendUpdateEmail(String toAccount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Update verification.\n");
        stringBuilder.append("Click to below link to complete update email\n");
        stringBuilder.append("Link: ");
        stringBuilder.append(serverHost).append("/api/v1/profile/public/email/update/");
        stringBuilder.append(JwtUtil.encodeToUpdateEmail(toAccount, SpringSecurityUtil.getProfileId()));
        sendEmail(toAccount, "Update email", stringBuilder.toString());
    }
}
