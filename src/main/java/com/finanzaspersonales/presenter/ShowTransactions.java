package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class ShowTransactions {

  private ShowTransactions() { }

  public static void showAll(@NotNull MainView view) {
    String prompts = "\n";
    prompts += UIFormatter.titleStyle("Showing transactions");
    prompts += UIFormatter.subtitleStyle("View format: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Summarized"), new MenuItem("Detailed")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendContent(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuHandler.handleMenuOption(menuItems, view);
    }

    if (input.equals("Summarized")) {
      showSummarized(view);
    } else {
      showDetailed(view);
    }

    view.showPrompt(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    InputReader.readString();
  }

  private static void showSummarized(@NotNull MainView view) {
    view.appendContent(UIFormatter.titleStyle("Summarized transactions:"));
    view.appendContent(
        TransactionFormatter.transactionsTable(Database.db().getAllTransactions()));
  }

  private static void showDetailed(@NotNull MainView view) {
    view.appendContent(UIFormatter.titleStyle("Detailed transactions:"));

    Transaction[] transactions = Database.db().getAllTransactions();
    for (Transaction t : transactions) {
      view.appendContent(
          TransactionFormatter.transactionDetailed(t));
    }
  }
}
