package com.finanzaspersonales.presenter.operations.category;

import com.finanzaspersonales.model.Categories;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.operations.Operation;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

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
public class DeleteCategory extends Operation {
  private final MenuItem[] deleteOptions =
      new MenuItem[] { new MenuItem("Single"), new MenuItem("All") };

  public DeleteCategory(MainView view) {
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
  public void delete() {
    startOperation();

    String input = processMenu(deleteOptions);

    if (input.equals("Single")) {
      deleteSingleCategory();
    } else {
      deleteAllCategories();
    }

    endOperation();
  }

  private void deleteSingleCategory() {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter category name", SimpleInput.TEXT));

    String name = SimpleInput.readString();
    if (Categories.exists(name)) {
      String warning = "\n";
      if (Categories.hasTransactions(name)) {
        warning += "This category has transactions associated with it.";
      }
      warning += "This operation is not reversible. Do you want to continue?";
      view.appendWithNewline(UIFormatter.warningStyle(warning));
      boolean choice = MenuInput.handleYesNo(view);

      if (choice) {
        Categories.delete(name);
        view.appendWithNewline(
            UIFormatter.successStyle("Category deleted."));
      }
    } else {
      view.appendWithNewline(UIFormatter.errorStyle("Invalid or non existent ID."));
    }
  }

  private void deleteAllCategories() {
    view.appendWithNewline(
        UIFormatter.warningStyle("All the recorded categories will be deleted."));
    view.appendWithNewline(
        UIFormatter.warningStyle("This operation is not reversible. Do you want to continue?"));

    boolean choice = MenuInput.handleYesNo(view);

    if (choice) {
      Categories.deleteAll();;
      view.appendWithNewline(
          UIFormatter.successStyle("Categories deleted."));
    }
  }
}
