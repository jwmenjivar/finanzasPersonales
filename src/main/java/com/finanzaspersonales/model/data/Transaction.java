package com.finanzaspersonales.model.data;

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
@RequiredArgsConstructor
public class Transaction {
  private final String uniqueID = UUID.randomUUID().toString();
  @NotNull private TransactionType type;
  private LocalDate date;
  private Category category;
  private String description = "";
  private double amount;

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