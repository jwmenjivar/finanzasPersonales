package com.finanzaspersonales.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Validates a category name.
 * Stores the result of the validation and any error messages if the amount is not valid.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class NameValidator {
  private String messages = "";
  private boolean isValid = false;

  /**
   * Verifies if the name is not null, empty, or already exists.
   */
  public boolean validateName(String name) {
    isValid = false;
    if (name == null) {
      messages += "Name can not be null.\n";
    } else if (name.isEmpty()) {
      messages += "Name can not be empty.\n";
    } else if (Database.db().categoryExists(name)) {
      messages += "Category name already exists. Input a unique name.\n";
    } else {
      isValid = true;
    }

    return isValid;
  }
}


