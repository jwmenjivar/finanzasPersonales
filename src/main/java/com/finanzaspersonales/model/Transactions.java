package com.finanzaspersonales.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class Transactions {

  private Transactions() {}

  public static void create(Transaction transaction) {
    Database.db().saveTransaction(transaction);
  }

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

  public static void update(Transaction transaction) {
    Database.db().updateTransaction(transaction);
  }

  public static Transaction[] getToday() {
    return Database.db().getTransactionsByDate(LocalDate.now());
  }

  public static Transaction[] getAll() {
    return Database.db().getAllTransactions();
  }

  public static Transaction getByID(String id) {
    return Database.db().getTransactionByID(id);
  }

  public static boolean exists(String id) {
    return Database.db().transactionExists(id);
  }

  public static void delete(String id) {
    Database.db().deleteTransaction(id);
  }

  public static void deleteAll() {
    Database.db().deleteAllTransactions();
  }
}
