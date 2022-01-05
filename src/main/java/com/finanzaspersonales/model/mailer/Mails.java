package com.finanzaspersonales.model.mailer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Sends exported files through email.
 * @author alex
 * @version 1.0
 * @since 1.0
 */
public class Mails {
    private final Mailer gmailMailer;
    private String from;
    private String pass;

    public Mails() {
        String[] dataMail = getDataEmail();
        from = dataMail[0];
        pass = dataMail[1];

        gmailMailer = new GmailMailer(from, pass);
    }

    /**
     * Sends a file through email.
     * @param pathFile Absolute path.
     * @param to Valid email address.
     */
    public void sendExportFile(String pathFile, String to) {
        gmailMailer.sendDocument(pathFile, from, to);
    }

    private String[] getDataEmail() {
        //  start
        String[] dataMail = {"",""};
        JSONParser jsonParser = new JSONParser();
        ClassLoader classLoader = Mails.class.getClassLoader();
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
