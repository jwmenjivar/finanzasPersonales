package com.finanzaspersonales.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Budgets {

  @NotNull
  public static Budget create(double monthlyAmount) {
    Budget budget = new Budget(monthlyAmount);
    Database.db().saveBudget(budget);
    return budget;
  }

  @Nullable
  public static Budget get() {
    return Database.db().getBudget();
  }
}
