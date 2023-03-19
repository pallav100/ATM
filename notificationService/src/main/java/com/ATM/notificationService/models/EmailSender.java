package com.ATM.notificationService.models;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    private static EmailSender es_instance = null;

    private String key="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";
    public SendGrid senderInstance;
    public EmailSender() {
        senderInstance = new SendGrid(key);
    }
    public static EmailSender getEmailSender(){
        if (es_instance == null)
                es_instance = new EmailSender();

            return es_instance;
    };

    public void sendEmail(EmailObject e) throws IOException {
        Mail mail = new Mail(e.getFrom(), e.getSubject(), e.getTo(), e.getContent());
        try {

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = senderInstance.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

}
