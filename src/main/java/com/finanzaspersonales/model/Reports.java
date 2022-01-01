package com.finanzaspersonales.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Locale;

public class Reports {

  private Reports() { }

  @NotNull
  public static Report calculateReport() {
    Report report = new Report();

    int thisYear = LocalDate.now().getYear();
    Transaction[] yearTransactions = Arrays.stream(Database.db().getAllTransactions())
        .filter(transaction -> transaction.getDate().getYear() == thisYear)
        .toArray(Transaction[]::new);
    Transaction[] expenses = Arrays.stream(yearTransactions)
        .filter(transaction -> transaction.getType() == Transaction.TransactionType.EXPENSE)
        .toArray(Transaction[]::new);
    Transaction[] income = Arrays.stream(yearTransactions)
        .filter(transaction -> transaction.getType() == Transaction.TransactionType.INCOME)
        .toArray(Transaction[]::new);

    // get the total amount expended and earned
    report.setYearExpenses(Arrays.stream(expenses)
        .mapToDouble(Transaction::getAmount).sum());
    report.setYearIncome(Arrays.stream(income)
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
