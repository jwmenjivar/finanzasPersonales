package com.finanzaspersonales.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public interface Database {
  /**
   * Returns an array of all the existing categories.
   */
  Category[] getAllCategories();

  /**
   * Returns an array of transactions by their type.
   */
  Category[] getCategoriesByType(TransactionType type);

  /**
   * Returns an array with all the transactions.
   */
  Transaction[] getAllTransactions();

  /**
   * Returns an array of all transactions by date.
   * Considers the day, month and year, but not the time.
   */
  Transaction[] getTransactionsByDate(LocalDate date);

  /**
   * Saves a transaction to the DB.
   * @param t
   */
  void saveTransaction(Transaction t);

  /**
   * Returns the database implementation.
   * @return
   */
  @NotNull
  @Contract(" -> new")
  static Database db() {
    return FakeDB.getInstance();
  }
}
