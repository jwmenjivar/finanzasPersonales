package com.finanzaspersonales.model;

import lombok.NoArgsConstructor;

/**
 * Validates a double amount for a transaction.
 * Stores the result of the validation and any error messages if the amount is not valid.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor
public final class TransactionAmountValidator extends Validator {

  /**
   * Validates if a double amount is correct for a transaction.
   * @return Returns true if the amount is greater than zero.
   */
  public boolean validate(double amount) {
    this.messages = "";
    this.isValid = amount > 0;

    if (!this.isValid) {
      this.messages = "The value cannot be zero.";
    }

    return this.isValid;
  }
}
