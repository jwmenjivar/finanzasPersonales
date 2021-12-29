package com.finanzaspersonales.presenter.ui;

import com.finanzaspersonales.model.Budget;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.WeekFields;

public class BudgetFormatter extends DataFormatter {
  private static final String MONTHLY = "Monthly";
  private static final String YEARLY = "Yearly";
  private static final String DAILY = "Daily";
  private static final String WEEKLY = "Weekly";
  private static final int DETAIL_SPACE = 10;
  private static final NumberFormat AMOUNT_FORMAT = NumberFormat.getCurrencyInstance();

  private BudgetFormatter() { }

  @NotNull
  public static String detailed(@NotNull Budget budget) {
    String formatted = "";
    String detailFormat = "%s %s\n";
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault()
            .a(formatInlineText(YEARLY + ":", DETAIL_SPACE)).reset().toString(),
        AMOUNT_FORMAT.format(budget.getMonthlyTotal() * 12));
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault()
            .a(formatInlineText(MONTHLY + ":", DETAIL_SPACE)).reset().toString(),
        Ansi.ansi().a(Ansi.Attribute.ITALIC).fgBrightMagenta()
            .a(AMOUNT_FORMAT.format(budget.getMonthlyTotal())).reset().toString());
    YearMonth currentYearMonth = YearMonth.now(ZoneId.systemDefault());
    int weeksInMonth = currentYearMonth.atEndOfMonth().get(WeekFields.ISO.weekOfMonth());
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault()
            .a(formatInlineText(WEEKLY + ":", DETAIL_SPACE)).reset().toString(),
        AMOUNT_FORMAT.format(budget.getMonthlyTotal() / weeksInMonth));
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault()
            .a(formatInlineText(DAILY + ":", DETAIL_SPACE)).reset().toString(),
        AMOUNT_FORMAT.format(budget.getMonthlyTotal() / YearMonth.now().lengthOfMonth()));

    return formatted;
  }
}
