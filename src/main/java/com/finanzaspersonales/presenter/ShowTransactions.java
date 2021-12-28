package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

public class ShowTransactions {

  private ShowTransactions() { }

  public static void showAll(@NotNull MainView view) {
    String prompts = "\n";
    prompts += UIFormatter.titleStyle("Showing transactions");
    prompts += UIFormatter.subtitleStyle("View format: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Summarized"), new MenuItem("Detailed")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuInput.handleMenu(menuItems, view);
    }

    if (input.equals("Summarized")) {
      showSummarized(view);
    } else {
      showDetailed(view);
    }

    view.appendWithoutNewline(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    SimpleInput.readString();
  }

  private static void showSummarized(@NotNull MainView view) {
    view.appendWithoutNewline(UIFormatter.titleStyle("Summarized transactions:"));
    view.appendWithNewline(
        TransactionFormatter.transactionsTable(Database.db().getAllTransactions()));
  }

  private static void showDetailed(@NotNull MainView view) {
    view.appendWithoutNewline(UIFormatter.titleStyle("Detailed transactions:"));

    Transaction[] transactions = Database.db().getAllTransactions();
    view.appendWithNewline(TransactionFormatter.transactionsDetailed(transactions));
  }
}
