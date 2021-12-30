package com.finanzaspersonales.presenter.ui;

import com.finanzaspersonales.model.Transaction;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

/**
 * Utility class with shared formatting methods for transaction and
 * category formatters.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
class DataFormatter {
  protected static final NumberFormat AMOUNT_FORMAT = NumberFormat.getCurrencyInstance();

  DataFormatter() { }

  /**
   * Formats the text so that content that would overflow the available text
   * is truncated.
   * @param space Maximum available space for the text [String length]
   * @return String with the formatted text and a length of size.
   */
  @NotNull
  protected static String formatInlineText(@NotNull String text, int space) {
    if (text.length() < space) {
      text = UIFormatter.textAlignLeft(text, space);
    } else if (text.length() > space) {
      text = text.substring(0, space - 3);
      text += "...";
    }

    return text;
  }

  /**
   * Formats a text with a color depending on the transaction type.
   * Colors: INCOME -> Green, EXPENSE -> Red
   * @return ANSI String with formatted amount
   */
  protected static String formatTextByType(String text, Transaction.TransactionType type) {
    return type == Transaction.TransactionType.INCOME ?
        Ansi.ansi().fgGreen().a(text).reset().toString() :
        Ansi.ansi().fgRed().a(text).reset().toString();
  }

  @NotNull
  protected static String formatDetail(String format, String term, String value, int space) {
    return UIFormatter.wrapText(String.format(
        format,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(term+ ":", space)).reset().toString(),
        value));
  }

  /**
   * Formats the amount so that it aligns to the right after the currency
   * symbol if the text doesn't fill the available space.
   * @return String with formatted amount
   */
  @NotNull
  protected static String formatInlineAmount(double amount, int space) {
    String formatted = AMOUNT_FORMAT.format(amount);
    if (amount < 0) {
      formatted = formatted.substring(1);
    }

    if (formatted.length() < space) {
      return formatted.charAt(0) +
          " ".repeat(space - formatted.length()) +
          formatted.substring(1);
    } else {
      return formatInlineText(formatted, space);
    }
  }
}
