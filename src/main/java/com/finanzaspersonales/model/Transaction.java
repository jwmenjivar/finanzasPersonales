package com.finanzaspersonales.model;

import lombok.Data;
import lombok.ToString;
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
@ToString
public class Transaction {
  private final String uniqueID;
  private double amount;
  private LocalDate date;
  private TransactionType type;
  private Category category;
  private String description;

  private Transaction() {
    this.date = LocalDate.now();
    this.uniqueID = UUID.randomUUID().toString();
  }

  /**
   * Makes a transaction of type INCOME.
   * @return Transaction
   */
  @NotNull
  public static Transaction makeIncomeTransaction() {
    Transaction t = new Transaction();
    t.type = TransactionType.INCOME;
    return t;
  }

  /**
   * Makes a transaction of type EXPENSE.
   * @return Transaction
   */
  @NotNull
  public static Transaction makeExpenseTransaction() {
    Transaction t = new Transaction();
    t.type = TransactionType.EXPENSE;
    return t;
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