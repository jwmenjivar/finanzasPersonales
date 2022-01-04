package com.finanzaspersonales.model;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Exports transactions to files.
 * @author mario
 * @version 1.0
 * @since 1.0
 */
public class Export {

  private Export() { }

  /**
   * Exports all the transactions into an Excel file.
   * @param filename Name of the exported file.
   */
  public static void exportTransactionsByYear(String filename, int year) throws IOException {
    // start
    String date;
    String valueAmount;

    try (HSSFWorkbook workbook = new HSSFWorkbook()) {
      HSSFSheet sheet = workbook.createSheet(filename);
      // Font to head
      HSSFFont fontHead = workbook.createFont();
      fontHead.setFontHeightInPoints((short) 12);
      fontHead.setColor(IndexedColors.WHITE.getIndex());
      fontHead.setBold(true);
      fontHead.setFontName("Calibri");

      // Styles of cell
      CellStyle style = workbook.createCellStyle();
      style.setFillBackgroundColor(IndexedColors.BLACK.index);
      style.setFillPattern(FillPatternType.BIG_SPOTS);
      style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
      style.setFont(fontHead);

      // ALL TRANSACTIONS SHEET
      // Create and style the header
      List<String> columns =
          Arrays.asList("#", "DATE", "AMOUNT", "TYPE", "CATEGORY", "DESCRIPTION");
      HSSFRow row = sheet.createRow(0);
      for (int i = 0; i < columns.size(); i++) {
        sheet.setColumnWidth(i, 4500);
        HSSFCell cell = row.createCell(i);
        cell.setCellValue(columns.get(i));
        cell.setCellStyle(style);
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
      }

      // Access the database to get all the transactions
      Transaction[] transactions = Arrays.stream(Transactions.getAll())
          .filter(transaction -> transaction.getDate().getYear() == year)
          .toArray(Transaction[]::new);

      // Write all the transactions row by row
      Locale locale = new Locale("en", "US");
      NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      for (int count = 0; count < transactions.length; count++) {
        // the first row is for the header, so always add + 1
        HSSFRow rows = sheet.createRow(count + 1);
        date = transactions[count].getDate().toString();
        valueAmount = currencyFormatter.format(transactions[count].getAmount());
        rows.createCell(0).setCellValue(count + 1);
        rows.createCell(1).setCellValue(date);
        rows.createCell(2).setCellValue(valueAmount);
        rows.createCell(3).setCellValue(transactions[count].getType().name());
        rows.createCell(4).setCellValue(transactions[count].getCategory().getName());
        rows.createCell(5).setCellValue(transactions[count].getDescription());
      }

      // TRANSACTIONS PER MONTH SHEETS
      // Export sheets to month
      int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
      for (int month : months) {
        String nameMonth = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
        // filter the transactions for that month and store them in an array
        Transaction[] monthly = Arrays.stream(transactions)
            .filter(transaction -> transaction.getDate().getMonth().getValue() == month)
            .toArray(Transaction[]::new);

        HSSFSheet sheetMonthly = workbook.createSheet("T-" + nameMonth);
        HSSFRow rowTitle = sheetMonthly.createRow(0);
        HSSFCell cellTitle = rowTitle.createCell(0);
        cellTitle.setCellValue("TRANSACTIONS - " + nameMonth.toUpperCase());

        HSSFRow rowHead = sheetMonthly.createRow(1);
        for (int i = 0; i < columns.size(); i++) {
          sheetMonthly.setColumnWidth(i, 4500);
          HSSFCell cellMonthly = rowHead.createCell(i);
          cellMonthly.setCellValue(columns.get(i));
          cellMonthly.setCellStyle(style);
          CellStyle cellStyle = cellMonthly.getCellStyle();
          cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }

        for (int j = 0; j < monthly.length; j++) {
          HSSFRow rowsMonthly = sheetMonthly.createRow(j + 2);
          date = monthly[j].getDate().toString();
          valueAmount = currencyFormatter.format(monthly[j].getAmount());
          rowsMonthly.createCell(0).setCellValue(j + 1);
          rowsMonthly.createCell(1).setCellValue(date);
          rowsMonthly.createCell(2).setCellValue(valueAmount);
          rowsMonthly.createCell(3).setCellValue(monthly[j].getType().name());
          rowsMonthly.createCell(4).setCellValue(monthly[j].getCategory().getName());
          rowsMonthly.createCell(5).setCellValue(monthly[j].getDescription());
        }
      }

      System.setProperty("log4j.configurationFile", "./path_to_the_log4j2_config_file/log4j2.xml");
      OutputStream out = new FileOutputStream(System.getProperty("user.dir") + "/" + filename);
      workbook.write(out);
    } catch (IOException e) {
      throw new IOException("Error exporting the transactions.\n" + e.getMessage());
    }
    //  end
  }
}
