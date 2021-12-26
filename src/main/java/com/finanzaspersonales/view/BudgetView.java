package com.finanzaspersonales.view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface BudgetView extends MainView {

  /**
   * Returns a new BudgetView implementation.
   * @return
   */
  @NotNull
  @Contract(" -> new")
  static BudgetView getBudgetView() {
    return new BudgetViewImpl();
  }
}
