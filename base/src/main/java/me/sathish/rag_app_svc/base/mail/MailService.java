package me.sathish.rag_app_svc.base.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;


@Service
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;
    private final TemplateEngine templateEngine;

    public MailService(final JavaMailSender javaMailSender, 
                      final MailProperties mailProperties,
                      final TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendMail(final String mailTo, final String subject, final String html) {
        log.info("sending mail {} to {}", subject, mailTo);

        javaMailSender.send(mimeMessage -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom(mailProperties.getMailFrom(), mailProperties.getMailDisplayName());
            message.setTo(mailTo);
            message.setSubject(subject);
            message.setText(html, true);
        });

        log.info("sending completed");
    }

    @Async
    public void sendMailWithTemplate(final String mailTo, final String subject, 
                                    final String templateName, final Map<String, Object> variables) {
        log.info("sending templated mail {} to {} using template {}", subject, mailTo, templateName);

        try {
            final Context context = new Context();
            context.setVariables(variables);
            final String htmlContent = templateEngine.process(templateName, context);

            javaMailSender.send(mimeMessage -> {
                final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom(mailProperties.getMailFrom(), mailProperties.getMailDisplayName());
                message.setTo(mailTo);
                message.setSubject(subject);
                message.setText(htmlContent, true);
            });

            log.info("templated mail sending completed");
        } catch (Exception e) {
            log.error("Failed to send templated email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
