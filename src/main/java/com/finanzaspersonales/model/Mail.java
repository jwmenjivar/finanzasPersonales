package com.finanzaspersonales.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Mail {

    private String from;
    private String pass;

    public void sendExportFile(String pathFile, String to) {
        //  start
        String[] dataMail = getDataEmail();
        from = dataMail[0];
        pass = dataMail[1];
        String subject = "Transactions sent";
        String sMmessage = "File with exported transactions\n";

        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        session.setDebug(false);

        try {

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(sMmessage);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File(pathFile));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentBodyPart);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException | IOException mex) {
            mex.printStackTrace();
        }
        //  end
    }

    private static String[] getDataEmail() {
        //  start
        String[] dataMail = {"",""};
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/src/main/resources/src_email.json" )) {
            Object obj = jsonParser.parse(reader);
            JSONArray arrayObj = (JSONArray) obj;
            JSONObject arrayData = (JSONObject) arrayObj.get(0);
            JSONObject appMail = (JSONObject) arrayData.get("data");
            dataMail[0] = (String) appMail.get("email");
            dataMail[1] = (String) appMail.get("password");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dataMail;
        //  end
    }
}
