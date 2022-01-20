package com.finanzaspersonales.model.db;

import com.finanzaspersonales.model.data.Report;
import com.finanzaspersonales.model.data.Transaction;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Locale;

/**
 * Generates a report.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class Reports {

  private Reports() { }

  /**
   * Calculates the expenses and income for the given year, and the total the current month,
   * the current week, and the current day.
   * @return Report instance with the calculated values.
   */
  @NotNull
  public static Report calculateYearReport(int year) {
    Report report = new Report();

    Transaction[] yearTransactions = Arrays.stream(Database.db().getAllTransactions())
        .filter(transaction -> transaction.getDate().getYear() == year)
        .toArray(Transaction[]::new);
    Transaction[] expenses = Arrays.stream(yearTransactions)
        .filter(transaction -> transaction.getType() == Transaction.TransactionType.EXPENSE)
        .toArray(Transaction[]::new);
    Transaction[] income = Arrays.stream(yearTransactions)
        .filter(transaction -> transaction.getType() == Transaction.TransactionType.INCOME)
        .toArray(Transaction[]::new);

    // get the total amount expended in the current year, month, week and day
    report.setYearExpenses(Arrays.stream(expenses)
        .mapToDouble(Transaction::getAmount).sum());
    report.setMonthExpenses(Arrays.stream(expenses)
        .filter(transaction -> transaction.getDate().getMonth() == LocalDate.now().getMonth())
        .mapToDouble(Transaction::getAmount).sum());
    report.setWeekExpenses(Arrays.stream(expenses)
        .filter(transaction -> inSameCalendarWeek(transaction.getDate(), LocalDate.now()))
        .mapToDouble(Transaction::getAmount).sum());
    report.setDayExpenses(Arrays.stream(expenses)
        .filter(transaction -> transaction.getDate().getDayOfMonth() == LocalDate.now()
                .getDayOfMonth())
        .mapToDouble(Transaction::getAmount).sum());

    // get the total amount earned in the current year, month, week and day
    report.setYearIncome(Arrays.stream(income)
        .mapToDouble(Transaction::getAmount).sum());
    report.setMonthIncome(Arrays.stream(income)
        .filter(transaction -> transaction.getDate().getMonth() == LocalDate.now().getMonth())
        .mapToDouble(Transaction::getAmount).sum());
    report.setWeekIncome(Arrays.stream(income)
        .filter(transaction -> inSameCalendarWeek(transaction.getDate(), LocalDate.now()))
        .mapToDouble(Transaction::getAmount).sum());
    report.setDayIncome(Arrays.stream(income)
        .filter(transaction -> transaction.getDate().getDayOfMonth() == LocalDate.now()
                .getDayOfMonth())
        .mapToDouble(Transaction::getAmount).sum());

    return report;
  }

  private static boolean inSameCalendarWeek(@NotNull LocalDate firstDate,
                                            @NotNull LocalDate secondDate) {
    // get a reference to the system of calendar weeks in your default locale
    WeekFields weekFields = WeekFields.of(Locale.getDefault());
    // find out the calendar week for each of the dates
    int firstDatesCalendarWeek = firstDate.get(weekFields.weekOfWeekBasedYear());
    int secondDatesCalendarWeek = secondDate.get(weekFields.weekOfWeekBasedYear());
    /*
     * find out the week based year, too,
     * two dates might be both in a calendar week number 1 for example,
     * but in different years
     */
    int firstWeekBasedYear = firstDate.get(weekFields.weekBasedYear());
    int secondWeekBasedYear = secondDate.get(weekFields.weekBasedYear());
    // return if they are equal or not
    return firstDatesCalendarWeek == secondDatesCalendarWeek
        && firstWeekBasedYear == secondWeekBasedYear;
  }
}
