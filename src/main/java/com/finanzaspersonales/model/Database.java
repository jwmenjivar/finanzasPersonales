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
  Category[] getCategoriesByType(Transaction.TransactionType type);

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
   * Returns a transaction by ID if it exists.
   * @param id UUID String
   * @return Transaction with the ID or null
   */
  Transaction getTransactionByID(String id);

  /**
   * Saves a transaction to the DB.
   */
  void saveTransaction(Transaction t);

  /**
   * Updates a transaction in the DB.
   */
  void updateTransaction(Transaction t);

  /**
   * Deletes the transaction with the same UUID.
   * @param id UUID String
   */
  void deleteTransaction(String id);

  /**
   * Deletes all the transactions in the DB.
   */
  void deleteAllTransactions();

  /**
   * Verifies if a transaction exists
   * @param id UUID String
   */
  boolean transactionExists(String id);

  /**
   * Returns the database implementation.
   */
  @NotNull
  @Contract(" -> new")
  static Database db() {
    return FakeDB.getInstance();
  }
}
