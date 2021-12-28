package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.model.TransactionType;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class that uses 'jansi' to format the way transactions are displayed
 * on screen.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class TransactionFormatter {
  private static final int DATE_SPACE = 10;
  private static final int AMOUNT_SPACE = 12;
  private static final int TYPE_SPACE = 10;
  private static final int TEXT_SPACE = 20;
  private static final int DETAIL_SPACE = 15;
  private static final String DATE_H = "Date";
  private static final String AMOUNT_H = "Amount";
  private static final String TYPE_H = "Type";
  private static final String CATEGORY_H = "Category";
  private static final String DESCRIPTION_H = "Description";
  private static final String NO_TRANSACTIONS = "<No transactions>";
  private static final NumberFormat AMOUNT_FORMAT = NumberFormat.getCurrencyInstance();

  private TransactionFormatter() {}

  /**
   * Formats a transaction into a single line string. Long texts are truncated.
   * Data order: Date > Amount > Type > Category > Description.
   * @param transaction
   * @return Single line ANSI String with formatted transaction
   */
  public static String transactionInline(@NotNull Transaction transaction) {
    // date type description category amount
    String formatted = "%s  %s  %s %s %s";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    return String.format(formatted,
        formatInlineText(transaction.getDate().format(dateTimeFormatter), DATE_SPACE),
        formatAmountType(formatInlineAmount(transaction.getAmount()), transaction.getType()),
        formatInlineText(transaction.getType().toString(), TYPE_SPACE),
        formatInlineText(transaction.getCategory().getName(), TEXT_SPACE),
        formatInlineText(transaction.getDescription(), TEXT_SPACE));
  }

  /**
   * Formats a list of transactions into a table. If no transactions are provided,
   * shows a placeholder message.
   * Data order: Date > Amount > Type > Category > Description.
   * @param transactions
   * @return Multiline ANSI String with a table of transactions
   */
  @NotNull
  public static String transactionsTable(@NotNull Transaction[] transactions) {
    StringBuilder formatted = new StringBuilder();

    // format the header
    formatted.append(
        Ansi.ansi().bg(Ansi.Color.BLACK).a(String.format(
            "  %s %s  %s %s %s %s",
            Ansi.ansi().bold().fgBright(Ansi.Color.WHITE).a("#").toString(),
            Ansi.ansi().bold().a(UIFormatter.center(DATE_H.toUpperCase(), DATE_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(AMOUNT_H.toUpperCase(), AMOUNT_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(TYPE_H.toUpperCase(), TYPE_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(CATEGORY_H.toUpperCase(), TEXT_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(DESCRIPTION_H.toUpperCase(), TEXT_SPACE))))
            .reset().toString()).append("\n");

    if (transactions.length > 0) {
      int count = 1;

      for (Transaction t : transactions) {
        formatted.append(String.format("   %s %s%n",
            Ansi.ansi().bold().a(String.valueOf(count)).reset().toString(),
            transactionInline(t)));
        count++;
      }
    } else {
      formatted.append(NO_TRANSACTIONS).append("\n");
    }

    return formatted.toString();
  }

  /**
   * Formats a transaction into a multiline String with the full data.
   * @param transaction
   * @return Multiline ANSI String with the transaction details
   */
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
        transaction.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(CATEGORY_H+ ":", DETAIL_SPACE)).reset().toString(),
        transaction.getCategory().getName());
    formatted += UIFormatter.wrapText(String.format(
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

  /**
   * Formats the amount so that it aligns to the right after the currency
   * symbol if the text doesn't fill the available space.
   * @param amount
   * @return String with formatted amount
   */
  @NotNull
  private static String formatInlineAmount(double amount) {
    String formatted = AMOUNT_FORMAT.format(amount);

    if (formatted.length() < AMOUNT_SPACE) {
      return formatted.charAt(0) +
          " ".repeat(AMOUNT_SPACE - formatted.length() - 1) +
          formatted.substring(1);
    } else {
      return formatInlineText(formatted, AMOUNT_SPACE);
    }
  }

  /**
   * Formats the text so that content that would overflow the available text
   * is truncated.
   * @param text
   * @param space Maximum available space for the text [String length]
   * @return String with the formatted text and a length of size.
   */
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

  /**
   * Formats an amount with a color depending on the transaction type.
   * Colors: INCOME -> Green, EXPENSE -> Red
   * @param amount
   * @param type
   * @return ANSI String with formatted amount
   */
  private static String formatAmountType(String amount, TransactionType type) {
    return type == TransactionType.INCOME ?
        Ansi.ansi().fgGreen().a(amount).reset().toString() :
        Ansi.ansi().fgRed().a(amount).reset().toString();
  }
}
