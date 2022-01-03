package com.finanzaspersonales.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a transaction category.
 * It must have a unique name and a transaction type.
 * @author joseph
 * @version 1.0
 * @since 1.0
 */
@Data
public class Category {
  private final String uniqueID = UUID.randomUUID().toString();
  @NotNull private Transaction.TransactionType type;
  @NotNull private String name;
  private String description = "";

  Category(@NotNull Transaction.TransactionType type, @NotNull String name,
           String description) {
    this.type = type;
    this.name = name;
    this.description = description;
  }
}