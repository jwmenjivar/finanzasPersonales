package com.finanzaspersonales.presenter.ui;

import com.finanzaspersonales.model.Transaction;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class with shared formatting methods for transaction and
 * category formatters.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
class DataFormatter {

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
      text = UIFormatter.textAlignLeft(text, space - text.length());
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
}
