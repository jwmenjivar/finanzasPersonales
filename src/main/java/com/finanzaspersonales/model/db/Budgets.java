package com.finanzaspersonales.model.db;

import com.finanzaspersonales.model.data.Budget;
import com.finanzaspersonales.model.db.Database;
import org.jetbrains.annotations.NotNull;

/**
 * Performs all the budget Database interactions.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class Budgets {
  private static final Budget budget = Database.db().getBudget();

  private Budgets() { }

  /**
   * Enables the budget using the given amount.
   * @return Budget instance.
   */
  @NotNull
  public static Budget enable(double monthlyAmount) {
    budget.setMonthlyTotal(monthlyAmount);
    Database.db().setBudget(budget);
    return get();
  }

  /**
   * @return Budget instance.
   */
  @NotNull
  public static Budget get() {
    return budget;
  }

  /**
   * Disables the current budget by setting the monthly value to zero.
   */
  public static void disable() {
    budget.setMonthlyTotal(0);
    Database.db().setBudget(budget);
  }
}
