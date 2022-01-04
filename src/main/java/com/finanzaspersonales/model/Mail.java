package com.finanzaspersonales.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Sends exported files through email.
 * @author alex
 * @version 1.0
 * @since 1.0
 */
public class Mail {
    private String from;
    private String pass;

    /**
     * Sends a file through email.
     * @param pathFile Absolute path.
     * @param to Valid email address.
     */
    public void sendExportFile(String pathFile, String to) {
        //  start
        String[] dataMail = getDataEmail();
        from = dataMail[0];
        pass = dataMail[1];
        String subject = "Transactions sent";
        String sMessage = "File with exported transactions\n";

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
            messageBodyPart.setText(sMessage);

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

    private String[] getDataEmail() {
        //  start
        String[] dataMail = {"",""};
        JSONParser jsonParser = new JSONParser();
        ClassLoader classLoader = Mail.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("src_email.json")) {
            assert inputStream != null;
            // from https://www.baeldung.com/convert-input-stream-to-string
            String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
            Object obj = jsonParser.parse(text);
            JSONObject arrayData = (JSONObject) obj;
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
