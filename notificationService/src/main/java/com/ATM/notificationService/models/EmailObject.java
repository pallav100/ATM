package com.ATM.notificationService.models;

import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;

public class EmailObject {
    private static EmailSender es = null;
    private com.sendgrid.helpers.mail.objects.Email from;
    private com.sendgrid.helpers.mail.objects.Email to;


    private String subject;
    private Content content;


    @Override
    public String toString() {
        return "EmailObject{" +
                "from=" + from.getEmail() +
                ", to=" + to.getEmail() +
                ", subject='" + subject + '\'' + '}';
    }

    public EmailObject(String fromId, String sub, String toId, String content) {
        this.from = new com.sendgrid.helpers.mail.objects.Email(fromId);
        this.subject = sub;
        this.to = new com.sendgrid.helpers.mail.objects.Email(toId);
        this.content = new Content("text/plain", content);

    }

    public Email getFrom() {
        return from;
    }

    public Email getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public Content getContent() {
        return content;
    }

}
