package com.finanzaspersonales.model.mailer;

interface Mailer {
  void sendDocument(String filepath, String from, String emailTo);
}
