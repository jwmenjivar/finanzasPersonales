package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.model.TransactionType;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TransactionFormatter {
  private static final int DATE_SPACE = 10;
  private static final int AMOUNT_SPACE = 10;
  private static final int TYPE_SPACE = 10;
  private static final int TEXT_SPACE = 20;
  private static final int DETAIL_SPACE = 15;
  private static final String DATE_H = "Date";
  private static final String AMOUNT_H = "Amount";
  private static final String TYPE_H = "Type";
  private static final String CATEGORY_H = "Category";
  private static final String DESCRIPTION_H = "Description";
  private static final String NO_TRANSACTIONS = "<No transactions>";
  private static final String SIMPLE_DATE = "dd-MM-yy";
  private static final NumberFormat AMOUNT_FORMAT = NumberFormat.getCurrencyInstance();

  private TransactionFormatter() {}

  public static String transactionInline(@NotNull Transaction transaction) {
    // date type description category amount
    String formatted = "%s %s  %s %s %s";
    return String.format(formatted,
        formatInlineText(
            new SimpleDateFormat(SIMPLE_DATE).format(transaction.getDate()),
            DATE_SPACE),
        formatAmountType(formatInlineAmount(transaction.getAmount()), transaction.getType()),
        formatInlineText(transaction.getType().toString(), TYPE_SPACE),
        formatInlineText(transaction.getCategory().getName(), TEXT_SPACE),
        formatInlineText(transaction.getDescription(), TEXT_SPACE));
  }

  @NotNull
  public static String transactionsTable(@NotNull List<Transaction> transactions) {
    StringBuilder formatted = new StringBuilder();
    formatted.append(
        String.format(
            "    %s %s %s %s %s %s",
            Ansi.ansi().bold().fgBright(Ansi.Color.WHITE).a("#").toString(),
            Ansi.ansi().bold().a(UIFormatter.center(DATE_H.toUpperCase(), DATE_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(AMOUNT_H.toUpperCase(), AMOUNT_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(TYPE_H.toUpperCase(), TYPE_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(CATEGORY_H.toUpperCase(), TEXT_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(DESCRIPTION_H.toUpperCase(), TEXT_SPACE))));
    formatted.append(
        Ansi.ansi().bg(Ansi.Color.BLACK).a(formatted).reset().toString()).append("\n");

    if (!transactions.isEmpty()) {
      int count = 1;

      for (Transaction t : transactions) {
        formatted.append(String.format("    %s %s%n",
            Ansi.ansi().bold().a(String.valueOf(count)).reset().toString(),
            transactionInline(t)));
        count++;
      }
    } else {
      formatted.append(NO_TRANSACTIONS).append("\n");
    }

    return formatted.toString();
  }

  @NotNull
  public static String transactionDetailed(@NotNull Transaction transaction) {
    String formatted = "";
    String detailFormat = "%s %s\n";
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(TYPE_H + ":", DETAIL_SPACE)).reset().toString(),
        transaction.getType().toString());
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(DATE_H + ":", DETAIL_SPACE)).reset().toString(),
        new SimpleDateFormat(SIMPLE_DATE).format(transaction.getDate()));
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(CATEGORY_H+ ":", DETAIL_SPACE)).reset().toString(),
        transaction.getCategory().getName());
    formatted += UIFormatter.formatText(String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(DESCRIPTION_H+ ":", DETAIL_SPACE)).reset().toString(),
        transaction.getDescription()));
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(AMOUNT_H+ ":", DETAIL_SPACE)).reset().toString(),
        formatAmountType(AMOUNT_FORMAT.format(transaction.getAmount()), transaction.getType()));

    return formatted;
  }

  @NotNull
  private static String formatInlineAmount(double amount) {
    String formatted = AMOUNT_FORMAT.format(amount);

    if (formatted.length() < AMOUNT_SPACE) {
      String sb = formatted.charAt(0) +
          " ".repeat(AMOUNT_SPACE - formatted.length() - 1) +
          formatted.substring(1);

      formatted = sb;
    }

    return formatted;
  }

  @NotNull
  private static String formatInlineText(@NotNull String text, int space) {
    if (text.length() < space) {
      text = UIFormatter.textAlignLeft(text, space - text.length());
    } else if (text.length() > space) {
      text = text.substring(0, space - 3);
      text += "...";
    }

    return text;
  }

  private static String formatAmountType(String amount, TransactionType type) {
    return type == TransactionType.INCOME ?
        Ansi.ansi().fgGreen().a(amount).reset().toString() :
        Ansi.ansi().fgRed().a(amount).reset().toString();
  }
}
