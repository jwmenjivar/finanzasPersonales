package com.finanzaspersonales.model;

public class Export {

  public static void exportAllTransactions() {
    Transaction[] transactions = Transactions.getAll();


    String dir = System.getProperty("user.dir");

    // directory from where the program was launched
    // e.g /home/mkyong/projects/core-java/java-io
    // System.out.println(dir);
  }
}
