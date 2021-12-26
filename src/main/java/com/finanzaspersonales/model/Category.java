package com.finanzaspersonales.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
public class Category {
  @Setter
  private TransactionType transactionType;
  private final String uniqueID = UUID.randomUUID().toString();
  private String name;

  public void setName(@NotNull String name) throws IllegalArgumentException {
    if(!name.isEmpty()) {
      this.name = name;
    } else {
      throw new IllegalArgumentException("Name cannot be empty");
    }
  }
}