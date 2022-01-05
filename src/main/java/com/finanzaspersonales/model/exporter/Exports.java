package com.finanzaspersonales.model.exporter;

import java.io.IOException;

/**
 * Exports transactions to files.
 * @author mario
 * @version 1.0
 * @since 1.0
 */
public class Exports {
  private static final Exporter exportExcel = new ExportExcel();

  private Exports() { }

  /**
   * Exports all the transactions into an Excel file.
   * @param filename Name of the exported file.
   */
  public static void exportTransactionsByYear(String filename, int year) throws IOException {
    exportExcel.exportYear(filename, year);
  }
}
