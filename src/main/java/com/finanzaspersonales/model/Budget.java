package com.finanzaspersonales.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.WeekFields;

@Data
@ToString
@AllArgsConstructor
public class Budget {
  private double monthlyTotal;

  public double getYearlyTotal() {
    return monthlyTotal * 12;
  }

  public double getWeeklyTotal() {
    YearMonth currentYearMonth = YearMonth.now(ZoneId.systemDefault());
    int weeksInMonth = currentYearMonth.atEndOfMonth().get(WeekFields.ISO.weekOfMonth());

    return monthlyTotal / weeksInMonth;
  }

  public double getDailyTotal() {
    return monthlyTotal / YearMonth.now().lengthOfMonth();
  }
}
