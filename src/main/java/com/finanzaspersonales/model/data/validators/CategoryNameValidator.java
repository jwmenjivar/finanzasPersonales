package com.finanzaspersonales.model.data.validators;

import com.finanzaspersonales.model.db.Categories;
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
public final class CategoryNameValidator extends Validator {

  /**
   * Verifies if the name is not null, empty, or already exists.
   */
  public void validate(String name) {
    this.messages = "";
    isValid = false;
    if (name == null) {
      messages += "Name can not be null.";
    } else if (name.isEmpty()) {
      messages += "Name can not be empty.";
    } else if (Categories.exists(name)) {
      messages += "Category name already exists. Input a unique name.";
    } else {
      isValid = true;
    }
  }
}


