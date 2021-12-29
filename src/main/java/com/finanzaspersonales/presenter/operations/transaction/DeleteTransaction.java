package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.presenter.operations.Operation;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.input.MenuInput;
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
public class DeleteTransaction extends Operation {
  private final MenuItem[] deleteOptions =
      new MenuItem[] { new MenuItem("Single"), new MenuItem("All") };

  public DeleteTransaction(MainView view) {
    super(view, "Deleting transactions", "Choose what to delete: ");
  }

  /**
   * Operation that deletes an existing transaction following these steps:
   * 1. Ask to delete a single transaction or all
   * 2. If single: asks for ID and deletes
   * 3. If all: asks for confirmation and deletes
   *
   * It performs a DB delete operation.
   */
  public void delete() {
    startOperation();

    String input = processMenu(deleteOptions);

    if (input.equals("Single")) {
      deleteSingleTransaction();
    } else {
      deleteAllTransactions();
    }

    endOperation();
  }

  private void deleteSingleTransaction() {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter ID", SimpleInput.TEXT));

    String id = SimpleInput.readString();
    if (Database.db().transactionExists(id)) {
      view.appendWithNewline("\n" +
          UIFormatter.warningStyle("This operation is not reversible. Do you want to continue?"));

      boolean choice = MenuInput.handleYesNo(view);

      if (choice) {
        Database.db().deleteTransaction(id);
        view.appendWithNewline(
            UIFormatter.successStyle("Transaction deleted."));
      }
    } else {
      view.appendWithNewline(UIFormatter.errorStyle("Invalid or non existent ID."));
    }
  }

  private void deleteAllTransactions() {
    view.appendWithNewline(
        UIFormatter.warningStyle("All the recorded transactions will be deleted."));
    view.appendWithNewline(
        UIFormatter.warningStyle("This operation is not reversible. Do you want to continue?"));

    boolean choice = MenuInput.handleYesNo(view);

    if (choice) {
      Database.db().deleteAllTransactions();
      view.appendWithNewline(
          UIFormatter.successStyle("Transactions deleted."));
    }
  }
}
