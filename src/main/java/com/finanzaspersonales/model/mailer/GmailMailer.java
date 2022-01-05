package com.finanzaspersonales.model.mailer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;

public class GmailMailer implements Mailer {
  private final Session session;

  public GmailMailer(String senderEmail, String pass) {
    String host = "smtp.gmail.com";
    Properties properties = System.getProperties();

    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    session = Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(senderEmail, pass);
      }
    });

    session.setDebug(false);
  }

  public void sendDocument(String filepath, String from, String emailTo) {
    //  start
    try {
      String subject = "Transactions sent";
      String sMessage = "File with exported transactions\n";

      MimeMessage message = new MimeMessage(session);

      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
      message.setSubject(subject);

      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setText(sMessage);

      MimeBodyPart attachmentBodyPart = new MimeBodyPart();
      attachmentBodyPart.attachFile(new File(filepath));

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
}
