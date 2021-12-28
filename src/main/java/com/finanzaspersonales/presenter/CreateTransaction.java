package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

public class CreateTransaction extends TransactionInput {

  private CreateTransaction() {
    super();
  }

  public static void create(@NotNull MainView view) {
    Transaction t = newTransaction(view);
    chooseCategory(view, t);
    assignDate(view, t);

    view.appendWithoutNewline(
        UIFormatter.subtitleStyle("Enter transaction amount and description:"));
    assignAmount(view, t);
    assignDescription(view, t);

    view.appendWithNewline(
        UIFormatter.successStyle("Transaction created."));
    view.appendWithNewline("\n" +
        UIFormatter.highlightStyle("New transaction:"));
    view.appendWithNewline(TransactionFormatter.transactionDetailed(t));

    Database.db().saveTransaction(t);
    view.appendWithoutNewline(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    SimpleInput.readString();
  }

  private static Transaction newTransaction(@NotNull MainView view) {
    String prompts = "\n";
    prompts += UIFormatter.titleStyle("Creating a new transaction");
    prompts += UIFormatter.subtitleStyle("Choose the transaction type: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Income"), new MenuItem("Expense")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuInput.handleMenu(menuItems, view);
    }

    if (input.equals("Income")) {
      return Transaction.makeIncomeTransaction();
    } else {
      return Transaction.makeExpenseTransaction();
    }
  }
}
