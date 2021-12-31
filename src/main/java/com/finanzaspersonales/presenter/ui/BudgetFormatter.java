package com.finanzaspersonales.presenter.ui;

import com.finanzaspersonales.model.Budget;
import com.finanzaspersonales.model.Report;
import com.finanzaspersonales.model.Transaction;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

public class BudgetFormatter extends DataFormatter {
  private static final String MONTHLY = "Monthly";
  private static final String YEARLY = "Yearly";
  private static final String DAILY = "Daily";
  private static final String WEEKLY = "Weekly";
  private static final int DETAIL_SPACE = 10;

  private BudgetFormatter() { }

  @NotNull
  public static String budgetSummary(@NotNull Budget budget, @NotNull Report report) {
    int termSpace = 10;
    double yearPercent = report.getYearExpenses()/budget.getYearlyTotal();
    double monthPercent = report.getMonthExpenses()/budget.getMonthlyTotal();
    double dayPercent = report.getDayExpenses()/budget.getDailyTotal();
    String format = "%s %s %s / %s%n";
    String yearSummary = summaryLine(format, "[Yearly]", yearPercent,
        report.getYearExpenses(), budget.getYearlyTotal(), termSpace);
    String monthSummary = summaryLine(format, "[Monthly]", monthPercent,
        report.getMonthExpenses(), budget.getMonthlyTotal(), termSpace);
    String daySummary = summaryLine(format, "[Daily]", dayPercent,
        report.getDayExpenses(), budget.getDailyTotal(), termSpace);
    return yearSummary + monthSummary + daySummary + "\n";
  }

  @NotNull
  public static String budgetReport(@NotNull Budget budget, @NotNull Report report) {
    int screenWidth = UIFormatter.maxWidth();
    int columnSpace = screenWidth / 4;

    String budgetH = UIFormatter.textAlignLeft("Budget".toUpperCase(), columnSpace);
    String expenseH = UIFormatter.textAlignLeft("Expense".toUpperCase(), columnSpace);
    String balanceH = UIFormatter.textAlignLeft("Balance".toUpperCase(), columnSpace);
    String yearlyH = UIFormatter.formatWithColor(Ansi.Color.BLUE,
        UIFormatter.textAlignRight(YEARLY, DETAIL_SPACE));
    String monthlyH = UIFormatter.formatWithColor(Ansi.Color.BLUE,
        UIFormatter.textAlignRight(MONTHLY, DETAIL_SPACE));
    String weeklyH = UIFormatter.formatWithColor(Ansi.Color.BLUE,
        UIFormatter.textAlignRight(WEEKLY, DETAIL_SPACE));
    String dailyH = UIFormatter.formatWithColor(Ansi.Color.BLUE,
        UIFormatter.textAlignRight(DAILY, DETAIL_SPACE));

    // format table
    String format = "%s %s %s %s";
    String budgetYearTotal = AMOUNT_FORMAT.format(budget.getYearlyTotal());
    String monthlyTotal = formatInlineAmount(budget.getMonthlyTotal(), budgetYearTotal.length());
    String weeklyTotal = formatInlineAmount(budget.getWeeklyTotal(), budgetYearTotal.length());
    String dailyTotal = formatInlineAmount(budget.getDailyTotal(), budgetYearTotal.length());

    String table = Ansi.ansi().bold().bg(Ansi.Color.BLACK)
        .a(String.format(format, " ".repeat(DETAIL_SPACE), budgetH, expenseH, balanceH))
        .reset().toString() + "\n";

    table += reportLine(format, yearlyH, budgetYearTotal, report.getYearExpenses(),
        budget.getYearlyTotal() - report.getYearExpenses(), columnSpace);
    table += reportLine(format, monthlyH, monthlyTotal, report.getMonthExpenses(),
        budget.getMonthlyTotal() - report.getMonthExpenses(), columnSpace);
    table += reportLine(format, weeklyH, weeklyTotal, report.getWeekExpenses(),
        budget.getWeeklyTotal() - report.getWeekExpenses(), columnSpace);
    table += reportLine(format, dailyH, dailyTotal, report.getDayExpenses(),
        budget.getDailyTotal() - report.getDayExpenses(), columnSpace);

    return table + "\n";
  }

  @NotNull
  public static String budgetDetailed(@NotNull Budget budget) {
    String formatted = "";
    String detailFormat = "%s %s\n";
    formatted += formatDetail(detailFormat, YEARLY,
        AMOUNT_FORMAT.format(budget.getYearlyTotal()), DETAIL_SPACE);
    formatted += formatDetail(detailFormat, MONTHLY,
        Ansi.ansi().a(Ansi.Attribute.ITALIC).fgBrightMagenta()
            .a(AMOUNT_FORMAT.format(budget.getMonthlyTotal())).reset().toString(),
        DETAIL_SPACE);
    formatted += formatDetail(detailFormat, WEEKLY,
        AMOUNT_FORMAT.format(budget.getWeeklyTotal()), DETAIL_SPACE);
    formatted += formatDetail(detailFormat, DAILY,
        AMOUNT_FORMAT.format(budget.getDailyTotal()), DETAIL_SPACE);

    return formatted;
  }

  private static String summaryLine(String format, String header, double percent,
                                    double report, double budget, int space) {
    return String.format(format,
        UIFormatter.formatWithColor(Ansi.Color.BLUE,
            UIFormatter.textAlignRight(header, space)),
        UIFormatter.formatWithColor(percentageColor(percent),
            UIFormatter.textAlignLeft(formatPercent(percent), space)),
        AMOUNT_FORMAT.format(report),
        AMOUNT_FORMAT.format(budget));
  }

  @NotNull
  private static String reportLine(String format, String rowName, String budget,
                                   double expense, double balance, int columnSpace) {
    return String.format(format,
        rowName,
        UIFormatter.textAlignLeft(budget, columnSpace),
        formatTextByType(
            UIFormatter.textAlignLeft(
                formatInlineAmount(expense, budget.length()),
                columnSpace),
            Transaction.TransactionType.EXPENSE),
        UIFormatter.formatWithColor(balanceColor(balance), UIFormatter.textAlignLeft(
                formatInlineAmount(balance, budget.length()),
                columnSpace))) + "\n";
  }

  private static Ansi.Color balanceColor(double balance) {
    if (balance < 0) {
      return Ansi.Color.RED;
    }
    return Ansi.Color.CYAN;
  }

  private static Ansi.Color percentageColor(double value) {
    if (0.75 < value && value < 1) {
      return Ansi.Color.YELLOW;
    } else if (value >= 1) {
      return Ansi.Color.RED;
    }

    return Ansi.Color.GREEN;
  }

  private static String formatPercent(double percent) {
    return String.format("(%.2f%%)", percent * 100);
  }
}
