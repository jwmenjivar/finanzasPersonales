package com.finanzaspersonales.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a transaction.
 * A transaction has a description, category, date, amount, and type.
 * @author joseph
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
public class Transaction {
  @Setter
  private String description;
  @Setter
  private Category category;
  @Setter
  private LocalDate date;
  private TransactionType type;
  private double amount;
  private final String uniqueID;

  private Transaction() {
    this.date = LocalDate.now();
    this.uniqueID = UUID.randomUUID().toString();
  }

  /**
   * Sets the transaction, validating that the value is more than zero.
   * @param amount Must be more than zero.
   */
  public void setAmount(double amount) throws IllegalArgumentException {
    if (amount > 0) {
      this.amount = amount;
    } else {
      throw new IllegalArgumentException("Amount must be more than zero");
    }
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
}