package com.finanzaspersonales.view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TransactionView extends MainView {

  /**
   * Returns a new TransactionView implementation.
   * @return
   */
  @NotNull
  @Contract(" -> new")
  static TransactionView getTransactionView() {
    return new TransactionViewImpl();
  }
}
