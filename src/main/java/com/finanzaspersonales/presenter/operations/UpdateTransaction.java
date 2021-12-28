package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.MainView;

/**
 * Operation to update an attribute of an existing transaction.
 * It asks for which attribute to modify, and then prompts asks
 * for the new value.
 *
 * To use, instantiate and provide the view where it will print all
 * the prompts.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class UpdateTransaction extends TransactionData {

  public UpdateTransaction(MainView view) {
    super(view,
        "Updating a transaction",
        "Enter an existing ID: ",
        "Transaction updated.");
  }

  /**
   * Operation that updates an existing transaction.
   * 1. Asks for the transaction ID.
   * 2. If it exists, asks for the attribute to edit.
   * 3. Asks for the new value.
   * 4. Updates the transaction.
   * 5. Ends the operation.
   *
   * It performs a DB save operation.
   */
  public void update() {
    startOperation();

    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter ID", SimpleInput.TEXT));

    String id = SimpleInput.readString();
    if (Database.db().transactionExists(id)) {
      transaction = Database.db().getTransactionByID(id);
      view.appendWithNewline("\n" + TransactionFormatter.transactionDetailed(transaction));

      view.appendWithoutNewline(UIFormatter.subtitleStyle("Choose what to edit: "));
      MenuItem[] menuItems = new MenuItem[]{
          new MenuItem(TransactionFormatter.AMOUNT_H),
          new MenuItem(TransactionFormatter.DATE_H),
          new MenuItem(TransactionFormatter.CATEGORY_H),
          new MenuItem(TransactionFormatter.DESCRIPTION_H),
          new MenuItem("Back")
      };
      view.appendWithNewline(UIFormatter.menuStyle(menuItems));

      String input = processMenu(menuItems);

      switch (input) {
        case TransactionFormatter.AMOUNT_H -> assignAmount();
        case TransactionFormatter.DATE_H -> assignDate();
        case TransactionFormatter.CATEGORY_H -> chooseCategory();
        case TransactionFormatter.DESCRIPTION_H -> assignDescription();
        default -> { /* go back */ }
      }

      if (!input.equals("Back")) {
        Database.db().updateTransaction(transaction);
        showResult();
      }
    } else {
      view.appendWithNewline(UIFormatter.errorStyle("Invalid or non existent ID."));
    }

    endOperation();
  }
}
