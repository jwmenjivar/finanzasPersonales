package com.finanzaspersonales.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Performs all the transactions DB operations.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class Transactions {

  private Transactions() {}

  /**
   * Creates a new transaction using the provided values.
   * @return A new transaction instance.
   */
  @NotNull
  public static Transaction create(@NotNull Transaction.TransactionType type, Category category,
                                   LocalDate date, double amount, String description) {
    Transaction transaction = new Transaction(type);
    transaction.setCategory(category);
    transaction.setDate(date != null ? date : LocalDate.now());
    transaction.setAmount(amount);
    transaction.setDescription(description != null ? description : "");

    Database.db().saveTransaction(transaction);

    return transaction;
  }

  /**
   * Updates an existing transaction using the values inside the provided instance.
   * @param transaction Transaction with an existing ID.
   */
  public static void update(Transaction transaction) {
    Database.db().updateTransaction(transaction);
  }

  /**
   * @return All transactions with date equal to LocalDate.now()
   */
  public static Transaction[] getToday() {
    return Database.db().getTransactionsByDate(LocalDate.now());
  }

  /**
   * @return All recorded transactions.
   */
  public static Transaction[] getAll() {
    return Database.db().getAllTransactions();
  }

  /**
   * @param id Existing ID.
   * @return Matching transaction.
   */
  public static Transaction getByID(String id) {
    return Database.db().getTransactionByID(id);
  }

  /**
   * Verifies if a transaction exists.
   */
  public static boolean exists(String id) {
    return Database.db().transactionExists(id);
  }

  /**
   * Deletes a transaction from the database.
   * @param id Existing ID.
   */
  public static void delete(String id) {
    Database.db().deleteTransaction(id);
  }

  /**
   * Deletes all transactions from the database.
   */
  public static void deleteAll() {
    Database.db().deleteAllTransactions();
  }

  public static boolean yearHasTransactions(int year) {
    Transaction[] transactions = getAll();
    return Arrays.stream(transactions)
        .anyMatch(transaction -> transaction.getDate().getYear() == year);
  }
}
