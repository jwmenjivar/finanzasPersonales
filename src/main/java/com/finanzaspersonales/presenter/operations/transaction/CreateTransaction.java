package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.Category;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.model.Transactions;
import com.finanzaspersonales.presenter.input.DataInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

import java.time.LocalDate;

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

  public CreateTransaction(View view) {
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
  @Override
  protected void operation() {
    Transaction.TransactionType type = inputType();
    Category category = inputCategory(type);
    LocalDate date = DataInput.inputDate(view);

    view.appendWithoutNewline(
        UIFormatter.subtitleStyle("Enter transaction amount and description:"));
    double amount = DataInput.inputAmount(view);
    String description = DataInput.inputDescription(view);

    Transaction transaction = Transactions.create(type, category, date, amount, description);
    showResult(transaction);
  }

  private Transaction.TransactionType inputType() {
    MenuItem[] typeOptions = new MenuItem[] {
        new MenuItem(Transaction.TransactionType.INCOME.name()),
        new MenuItem(Transaction.TransactionType.EXPENSE.name())
    };

    String input = MenuInput.processMenu(typeOptions, view);

    if (input.equals(Transaction.TransactionType.INCOME.name())) {
      return Transaction.TransactionType.INCOME;
    } else {
      return Transaction.TransactionType.EXPENSE;
    }
  }
}
