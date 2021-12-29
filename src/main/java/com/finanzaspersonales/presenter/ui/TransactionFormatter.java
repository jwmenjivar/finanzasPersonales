package com.finanzaspersonales.presenter.ui;

import com.finanzaspersonales.model.Transaction;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

/**
 * Utility class that uses 'jansi' to format the way transactions are displayed
 * on screen.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class TransactionFormatter extends DataFormatter {
  private static final int DATE_SPACE = 10;
  private static final int AMOUNT_SPACE = 12;
  private static final int TYPE_SPACE = 10;
  private static final int CATEGORY_SPACE = 19;
  private static final int TEXT_SPACE = 20;
  private static final int DETAIL_SPACE = 15;
  private static final int ID_SPACE = 4;
  public static final String DATE_H = "Date";
  public static final String AMOUNT_H = "Amount";
  public static final String TYPE_H = "Type";
  public static final String ID_H = "Transaction ID";
  public static final String CATEGORY_H = "Category";
  public static final String DESCRIPTION_H = "Description";
  private static final String NO_TRANSACTIONS = "<No transactions>";
  private static final NumberFormat AMOUNT_FORMAT = NumberFormat.getCurrencyInstance();

  private TransactionFormatter() {}

  /**
   * Formats a transaction into a single line string. Long texts are truncated.
   * Data order: Date > Amount > Type > Category > Description.
   * @return Single line ANSI String with formatted transaction
   */
  public static String transactionInline(@NotNull Transaction transaction) {
    // date type description category amount
    // date amount type category description
    String formatted = "%s  %s  %s %s %s";
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    return String.format(formatted,
        formatInlineText(transaction.getDate().format(dateTimeFormatter), DATE_SPACE),
        formatTextByType(
            formatInlineAmount(transaction.getAmount()), transaction.getType()),
        formatInlineText(transaction.getType().toString(), TYPE_SPACE),
        formatInlineText(transaction.getCategory().getName(), CATEGORY_SPACE),
        formatInlineText(transaction.getDescription(), TEXT_SPACE));
  }

  /**
   * Formats a list of transactions into a table. If no transactions are provided,
   * shows a placeholder message.
   * Data order: Date > Amount > Type > Category > Description.
   * @return Multiline ANSI String with a table of transactions
   */
  @NotNull
  public static String transactionsTable(@NotNull Transaction[] transactions) {
    StringBuilder formatted = new StringBuilder();

    // format the header
    formatted.append(
        // # date amount type category description
        Ansi.ansi().bg(Ansi.Color.BLACK).a(String.format(
            "%s %s  %s %s %s  %s",
            Ansi.ansi().bold().fgBright(Ansi.Color.WHITE).a(
                formatInlineText("#", ID_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(DATE_H.toUpperCase(), DATE_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(AMOUNT_H.toUpperCase(), AMOUNT_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(TYPE_H.toUpperCase(), TYPE_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(CATEGORY_H.toUpperCase(), CATEGORY_SPACE)),
            Ansi.ansi().bold().a(UIFormatter.center(DESCRIPTION_H.toUpperCase(), TEXT_SPACE))))
            .reset().toString()).append("\n");

    if (transactions.length > 0) {
      int count = 1;

      for (Transaction t : transactions) {
        formatted.append(String.format("%s %s%n",
            Ansi.ansi().bold().a(
                formatInlineText(String.valueOf(count), ID_SPACE)).reset().toString(),
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
   * @return Multiline ANSI String with the transaction details
   */
  @NotNull
  public static String transactionDetailed(@NotNull Transaction transaction) {
    String formatted = "";
    String detailFormat = "%s %s\n";
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(Ansi.Attribute.UNDERLINE)
            .a(formatInlineText(ID_H + ":", DETAIL_SPACE)).reset().toString(),
        transaction.getUniqueID());
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
        formatTextByType(
            AMOUNT_FORMAT.format(transaction.getAmount()), transaction.getType()));

    return formatted;
  }

  @NotNull
  @Contract(pure = true)
  public static String transactionsDetailed(@NotNull Transaction[] transactions) {
    StringBuilder formatted = new StringBuilder();

    if (transactions.length > 0) {
      for (Transaction t : transactions) {
        formatted.append(transactionDetailed(t));
      }
    } else {
      formatted.append(NO_TRANSACTIONS).append("\n");
    }

    return formatted.toString();
  }

  /**
   * Formats the amount so that it aligns to the right after the currency
   * symbol if the text doesn't fill the available space.
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
}
