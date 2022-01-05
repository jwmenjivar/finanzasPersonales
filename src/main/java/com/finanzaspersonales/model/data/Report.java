package com.finanzaspersonales.model.data;

import lombok.Data;

/**
 * Represents a report with the current year, current month, current week,
 * and current day expense and income totals.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
@Data
public class Report {
  private double yearExpenses;
  private double yearIncome;
  private double monthExpenses;
  private double monthIncome;
  private double dayExpenses;
  private double dayIncome;
  private double weekExpenses;
  private double weekIncome;
}
