package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

/**
 * Operation to create a new transaction.
 * It asks for all the necessary data, validating it as it goes.
 *
 * To use, instantiate and provide the view where it will print all
 * the prompts.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class CreateTransaction extends TransactionData {
  private final MenuItem[] typeOptions = new MenuItem[] {
      new MenuItem(Transaction.TransactionType.INCOME.name()),
      new MenuItem(Transaction.TransactionType.EXPENSE.name())
  };

  public CreateTransaction(MainView view) {
    super(view,
        "Creating a new transaction",
        "Choose the transaction type: ",
        "Transaction created.");
  }

  /**
   * Operation that creates a new transaction following these steps:
   * 1. Ask for the transaction type
   * 2. Ask for the category
   * 3. Ask for the date
   * 4. Ask for the amount
   * 5. Ask for the description
   *
   * It performs a DB save operation.
   */
  public void create() {
    startOperation();

    this.transaction = newTransaction();
    chooseCategory();
    assignDate();

    view.appendWithoutNewline(
        UIFormatter.subtitleStyle("Enter transaction amount and description:"));
    assignAmount();
    assignDescription();

    showResult();

    Database.db().saveTransaction(this.transaction);
    endOperation();
  }

  private Transaction newTransaction() {
    String input = processMenu(typeOptions);

    if (input.equals(Transaction.TransactionType.INCOME.name())) {
      return Transaction.makeIncomeTransaction();
    } else {
      return Transaction.makeExpenseTransaction();
    }
  }
}
