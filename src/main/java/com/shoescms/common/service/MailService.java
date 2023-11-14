package com.shoescms.common.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final Logger mailLogger = LoggerFactory.getLogger(this.getClass());

    @Value("noreply@animenews.life")
    String from;

    public boolean sendMail(String to, String subject, String content) {
        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.setSubject(subject, "utf-8");
            mimeMessage.setText(content, "utf-8", "html");
        };

        try {
            javaMailSender.send(preparator);
            return true;
        } catch (MailException me) {
            log.error("MailException", me);
            return false;
        }
    }

    public void sendEmail(String templatePath, String to, String subject, Map<String, Object> content) throws MessagingException {
        mailLogger.info("Begin send mail");
        MimeMessage message = javaMailSender.createMimeMessage(); // init new SimpleMailMessage
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        //add variable
        Context context = new Context();
        context.setVariables(content);

        String contentHtml = templateEngine.process(templatePath, context);

        // set recipient, subject, content
        messageHelper.setFrom("noreply@animenews.life");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(contentHtml, true);

        javaMailSender.send(message); // send mail
        mailLogger.info("Send mail to ".concat(to.concat(" successfully!")));
    }
}
