package com.finanzaspersonales.model;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Export {

  public static void exportAllTransactions() throws IOException {
    // start
    String date, valueAmount;
    HSSFWorkbook workbook   = new HSSFWorkbook();
    ArrayList<String> columns = new ArrayList<>(Arrays.asList("#","DATE","AMOUNT","TYPE","CATEGORY","DESCRIPTION"));
    Locale locale           = new Locale("en","US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    DecimalFormat formatAmount = new DecimalFormat("#,###.00");

    //  DB
    Transaction[] transactions = Transactions.getAll();
//    transactions = Arrays.stream(transactions).filter(transaction -> transaction.getDate().getMonth().getValue() == 1 ).toArray(Transaction[]::new);


    HSSFSheet sheet         = workbook.createSheet("Transactions");
    // Font to head
    HSSFFont fontHead = workbook.createFont();
    fontHead.setFontHeightInPoints((short)12);
    fontHead.setColor(IndexedColors.WHITE.getIndex());
    fontHead.setBold(true);
    fontHead.setFontName("Calibri");

    // Styles of cell
    CellStyle style = workbook.createCellStyle();
    style.setFillBackgroundColor(IndexedColors.BLACK.index);
    style.setFillPattern(FillPatternType.BIG_SPOTS);
    style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    style.setFont(fontHead);

    //
    HSSFRow row = sheet.createRow(0);
    for (int i = 0 ; i < columns.size() ; i++){
      HSSFCell cell = row.createCell(i);
      cell.setCellValue(columns.get(i));
      cell.setCellStyle(style);
    }

    //    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    for (int j=0; j< transactions.length;j++) {
      HSSFRow rows = sheet.createRow(j+1);
      date = transactions[j].getDate().toString();
      valueAmount = currencyFormatter.format(transactions[j].getAmount());
      rows.createCell(0).setCellValue(j+1);
      rows.createCell(1).setCellValue(date);
      rows.createCell(2).setCellValue(valueAmount);
      rows.createCell(3).setCellValue(transactions[j].getType().name());
      rows.createCell(4).setCellValue(transactions[j].getCategory().getName());
      rows.createCell(5).setCellValue(transactions[j].getDescription());
    }

    // Export sheets to month
    int months[] = {1,2,3,4,5,6,7,8,9,10,11,12};

    for (int month: months ) {
      String nameMonth = Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault());
      Transaction[] monthly = Arrays.stream(transactions).filter(transaction -> transaction.getDate().getMonth().getValue() == month).toArray(Transaction[]::new);

      HSSFSheet sheetMonthly         = workbook.createSheet("Month_"+nameMonth);
      HSSFRow rowTitle = sheetMonthly.createRow(0);
      HSSFCell cellTitle = rowTitle.createCell(0);
      cellTitle.setCellValue(nameMonth.toUpperCase());

      HSSFRow rowHead = sheetMonthly.createRow(1);
      for (int i = 0 ; i < columns.size() ; i++){
        HSSFCell cellMonthly = rowHead.createCell(i);
        cellMonthly.setCellValue(columns.get(i));
        cellMonthly.setCellStyle(style);
      }

      for (int j=0; j< monthly.length;j++) {

        HSSFRow rowsMonthly = sheetMonthly.createRow(j+2);
        date = monthly[j].getDate().toString();
        valueAmount = currencyFormatter.format(monthly[j].getAmount());
        rowsMonthly.createCell(0).setCellValue(j+1);
        rowsMonthly.createCell(1).setCellValue(date);
        rowsMonthly.createCell(2).setCellValue(valueAmount);
        rowsMonthly.createCell(3).setCellValue(monthly[j].getType().name());
        rowsMonthly.createCell(4).setCellValue(monthly[j].getCategory().getName());
        rowsMonthly.createCell(5).setCellValue(monthly[j].getDescription());
      }
    }

    System.setProperty("log4j.configurationFile","./path_to_the_log4j2_config_file/log4j2.xml");
    OutputStream out = new FileOutputStream("src/main/resources/Transactions.xls");
    workbook.write(out);

    //    String dir = System.getProperty("user.dir");
    // directory from where the program was launched
    // e.g /home/mkyong/projects/core-java/java-io
    // System.out.println(dir);
    //    for (int i = 0; i < transactions.length; i++) {
    //      System.out.println(transactions[i].getCategory().getName());
    //    }
    // end
  }

}
