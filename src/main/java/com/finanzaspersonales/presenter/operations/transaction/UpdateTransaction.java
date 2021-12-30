package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.model.Transactions;
import com.finanzaspersonales.presenter.input.DataInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.View;

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

  public UpdateTransaction(View view) {
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
   *
   * It performs a DB update operation.
   */
  @Override
  protected void operation() {
    view.append(
        UIFormatter.promptStyle("Enter ID", SimpleInput.TEXT));

    String id = SimpleInput.readString();
    if (Transactions.exists(id)) {
      Transaction transaction = Transactions.getByID(id);
      view.append("\n" +
          TransactionFormatter.transactionDetailed(transaction));

      view.append(UIFormatter.subtitleStyle("Choose what to edit: "));
      MenuItem[] menuItems = new MenuItem[]{
          new MenuItem(TransactionFormatter.AMOUNT_H),
          new MenuItem(TransactionFormatter.DATE_H),
          new MenuItem(TransactionFormatter.CATEGORY_H),
          new MenuItem(TransactionFormatter.DESCRIPTION_H),
          new MenuItem("Back")
      };

      String input = MenuInput.processMenu(menuItems, view);

      switch (input) {
        case TransactionFormatter.AMOUNT_H ->
            transaction.setAmount(DataInput.inputAmount(view));
        case TransactionFormatter.DATE_H ->
            transaction.setDate(DataInput.inputDate(view));
        case TransactionFormatter.CATEGORY_H ->
            transaction.setCategory(inputCategory(transaction.getType()));
        case TransactionFormatter.DESCRIPTION_H ->
            transaction.setDescription(DataInput.inputDescription(view));
        default -> { /* go back */ }
      }

      if (!input.equals("Back")) {
        Transactions.update(transaction);
        showResult(transaction);
      }
    } else {
      view.append(UIFormatter.errorStyle("Invalid or non existent ID."));
    }
  }
}
