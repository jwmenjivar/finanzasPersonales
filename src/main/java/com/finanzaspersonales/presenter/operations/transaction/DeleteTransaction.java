package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.db.Transactions;
import com.finanzaspersonales.presenter.operations.ModelOperation;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.input.MenuInput;
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
public class DeleteTransaction extends ModelOperation {
  private final MenuItem[] deleteOptions =
      new MenuItem[] { new MenuItem("Single"), new MenuItem("All") };

  public DeleteTransaction(View view) {
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
  @Override
  protected void operation() {
    String input = MenuInput.processMenu(deleteOptions, view);

    if (input.equals("Single")) {
      deleteSingleTransaction();
    } else {
      deleteAllTransactions();
    }
  }

  private void deleteSingleTransaction() {
    view.append(
        UIFormatter.promptStyle("Enter ID", SimpleInput.TEXT));

    String id = SimpleInput.readString();
    if (Transactions.exists(id)) {
      view.warning("This operation is not reversible. Do you want to continue?");

      boolean choice = MenuInput.handleYesNo(view);

      if (choice) {
        Transactions.delete(id);
        view.success("Transaction deleted.");
      }
    } else {
      view.error("Invalid or non existent ID.");
    }
  }

  private void deleteAllTransactions() {
    view.warning("All the recorded transactions will be deleted.\n"+
        "This operation is not reversible. Do you want to continue?");

    boolean choice = MenuInput.handleYesNo(view);

    if (choice) {
      Transactions.deleteAll();
      view.success("Transactions deleted.");
    }
  }
}
