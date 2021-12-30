package com.finanzaspersonales.model;

import lombok.Data;

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

  Report() {}
}
