package com.tinydoc.utils;

import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class SendMail {

    public static final String remitente = "noreply.tinydoc";

    public static void senMail(String destinatario, String asunto, String cuerpo) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "casa7526808");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("noreply.tinydoc"));
            Address to = new InternetAddress(destinatario);
            message.addRecipient(Message.RecipientType.TO, to);
            message.setSubject(asunto);
            message.setText(cuerpo, null, "html");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com",
                    remitente, "casa7526808");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }


}
