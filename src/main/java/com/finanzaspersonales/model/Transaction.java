package com.finanzaspersonales.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

@Getter
@ToString
public class Transaction {
  @Setter
  private String description;
  @Setter
  private Category category;
  @Setter
  private Date date;
  private double amount;
  private TransactionType type;
  private final String uniqueID = UUID.randomUUID().toString();

  private Transaction() { }

  public void setAmount(double amount) throws IllegalArgumentException {
    if (amount > 0) {
      this.amount = amount;
    } else {
      throw new IllegalArgumentException("Amount must be more than zero");
    }
  }

  @NotNull
  public static Transaction makeIncomeTransaction() {
    Transaction t = new Transaction();
    t.type = TransactionType.INCOME;
    return t;
  }

  @NotNull
  public static Transaction makeExpenseTransaction() {
    Transaction t = new Transaction();
    t.type = TransactionType.EXPENSE;
    return t;
  }
}