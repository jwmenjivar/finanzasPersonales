package com.finanzaspersonales.model;

import lombok.Data;

import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.WeekFields;

/**
 * Represents a budget with a monthly total.
 * Calculates the daily, weekly and yearly total's based of the monthly total.
 * There can only be one budget entry. It is either active or inactive.
 * When the monthly total is equal to zero, it is inactive.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
@Data
public class Budget {
  private double monthlyTotal;

  Budget(double amount) { monthlyTotal = amount; }

  /**
   * Multiplies the monthly total by 12 to calculate the current year's budget.
   * @return Yearly budget.
   */
  public double getYearlyTotal() {
    return monthlyTotal * 12;
  }

  /**
   * Divides the monthly total by the current's month total weeks to calculate a week budget
   * for the current month.
   * @return Weekly budget.
   */
  public double getWeeklyTotal() {
    YearMonth currentYearMonth = YearMonth.now(ZoneId.systemDefault());
    int weeksInMonth = currentYearMonth.atEndOfMonth().get(WeekFields.ISO.weekOfMonth());

    return monthlyTotal / weeksInMonth;
  }

  /**
   * Divides the monthly total by the current's month length to calculate a daily budget for the
   * current month.
   * @return Daily budget.
   */
  public double getDailyTotal() {
    return monthlyTotal / YearMonth.now().lengthOfMonth();
  }

  /**
   * Returns whether the budget is enabled at the moment.
   * @return True if the monthly total is greater than zero.
   */
  public boolean isEnabled() {
    return monthlyTotal > 0;
  }

  /**
   * Prevent the budget object to accept changes to the monthly value outside the model package.
   * @param amount Double greater than zero.
   */
  void setMonthlyTotal(double amount) {
    // never allow values less than zero.
    monthlyTotal = amount < 0 ? 0 : amount;
  }
}
