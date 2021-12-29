package com.finanzaspersonales.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a transaction.
 * A transaction has a description, category, date, amount, and type.
 * @author joseph
 * @version 1.0
 * @since 1.0
 */
@Data
public class Transaction {
  private final String uniqueID = UUID.randomUUID().toString();
  @NotNull private TransactionType type;
  private LocalDate date;
  private Category category;
  private String description = "";
  private double amount;

  protected Transaction(@NotNull TransactionType type) {
    this.type = type;
  }

  /**
   * Makes a transaction of type INCOME.
   * @return Transaction
   */
  @NotNull
  public static Transaction makeIncomeTransaction() {
    return new Transaction(TransactionType.INCOME);
  }

  /**
   * Makes a transaction of type EXPENSE.
   * @return Transaction
   */
  @NotNull
  public static Transaction makeExpenseTransaction() {
    return new Transaction(TransactionType.EXPENSE);
  }

  /**
   * Represents the transaction type.
   * Every transaction is either an income or expense transaction.
   * Transactions and Categories must have a transaction type.
   * @author joseph
   * @version 1.0
   * @since 1.0
   */
  public enum TransactionType {
    INCOME, EXPENSE
  }
}