package com.finanzaspersonales.presenter.operations.category;

import com.finanzaspersonales.model.Categories;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.operations.Procedure;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.view.View;

/**
 * Operation to delete an existing transaction.
 * It asks for the transaction ID.
 * If the ID exists, it asks for confirmation and deletes accordingly.
 * If the ID doesn't exist, it shows an error message and ends the operation.
 *
 * To use, instantiate and provide the view where it will print all
 * the prompts.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class DeleteCategory extends Procedure {
  private final MenuItem[] deleteOptions =
      new MenuItem[] { new MenuItem("Single"), new MenuItem("All") };

  public DeleteCategory(View view) {
    super(view, "Deleting transactions", "Choose what to delete: ");
  }

  /**
   * Operation that deletes an existing category following these steps:
   * 1. Ask to delete a single category or all
   * 2. If single: asks for name and deletes
   * 3. If all: asks for confirmation and deletes
   *
   * It performs a DB delete operation.
   */
  @Override
  protected void operation() {
    String input = MenuInput.processMenu(deleteOptions, view);

    if (input.equals("Single")) {
      deleteSingleCategory();
    } else {
      deleteAllCategories();
    }
  }

  private void deleteSingleCategory() {
    view.prompt("Enter category name", SimpleInput.TEXT);

    String name = SimpleInput.readString();
    if (Categories.exists(name)) {
      String warning = "";
      if (Categories.hasTransactions(name)) {
        warning += "This category has transactions associated with it.";
      }
      warning += "\nThis operation is not reversible. Do you want to continue?";
      view.warning(warning);
      boolean choice = MenuInput.handleYesNo(view);

      if (choice) {
        Categories.delete(name);
        view.success("Category deleted.");
      }
    } else {
      view.error("Invalid or non existent ID.");
    }
  }

  private void deleteAllCategories() {
    view.warning("All the recorded categories will be deleted.\n" +
        "This operation is not reversible. Do you want to continue?");

    boolean choice = MenuInput.handleYesNo(view);

    if (choice) {
      Categories.deleteAll();
      view.success("Categories deleted.");
    }
  }
}
