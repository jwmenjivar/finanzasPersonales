package com.finanzaspersonales.model.data;

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
@AllArgsConstructor
public class Category {
  private final String uniqueID = UUID.randomUUID().toString();
  @NotNull private Transaction.TransactionType type;
  @NotNull private String name;
  private String description = "";
}