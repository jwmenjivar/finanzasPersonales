package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

public class DeleteTransaction {

  private DeleteTransaction() { }

  public static void delete(@NotNull MainView view) {
    String prompts = "\n";
    prompts += UIFormatter.titleStyle("Deleting transactions");
    prompts += UIFormatter.subtitleStyle("Choose what to delete: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Single"), new MenuItem("All")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuInput.handleMenu(menuItems, view);
    }

    if (input.equals("Single")) {
      deleteSingleTransaction(view);
    } else {
      deleteAllTransactions(view);
    }

    view.appendWithoutNewline(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    SimpleInput.readString();
  }

  private static void deleteSingleTransaction(@NotNull MainView view) {
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

  private static void deleteAllTransactions(@NotNull MainView view) {
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
