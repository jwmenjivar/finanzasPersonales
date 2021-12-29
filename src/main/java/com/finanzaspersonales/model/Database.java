package com.finanzaspersonales.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public interface Database {

  /**
   * Retrieves an array with all the transactions.
   */
  Transaction[] getAllTransactions();

  /**
   * Retrieves an array of all transactions by date.
   * Considers the day, month and year, but not the time.
   */
  Transaction[] getTransactionsByDate(LocalDate date);

  /**
   * Retrieves a transaction by ID if it exists.
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
   * Retrieves an array of all the existing categories.
   */
  Category[] getAllCategories();

  /**
   * Retrieves an array of transactions by their type.
   */
  Category[] getCategoriesByType(Transaction.TransactionType type);

  /**
   * Retrieves a category from the DB by name.
   */
  Category getCategoryByName(String name);

  /**
   * Saves a category to the DB.
   */
  void saveCategory(Category category);

  /**
   * Updates a category to the DB.
   */
  void updateCategory(Category category);

  /**
   * Deletes a category from the DB.
   */
  void deleteCategory(String name);

  /**
   * Deletes all categories from the DB.
   */
  void deleteAllCategories();

  /**
   * Verifies if a category exists.
   * @param name Unique category name
   */
  boolean categoryExists(String name);

  boolean categoryHasTransactions(String name);

  /**
   * Returns the database implementation.
   */
  @NotNull
  @Contract(" -> new")
  static Database db() {
    return FakeDB.getInstance();
  }
}
