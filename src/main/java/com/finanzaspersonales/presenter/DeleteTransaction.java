package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Database;
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
    view.appendWithNewLine(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuHandler.handleMenu(menuItems, view);
    }

    if (input.equals("Single")) {
      deleteSingleTransaction(view);
    } else {
      deleteAllTransactions(view);
    }

    view.appendWithoutNewline(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    InputReader.readString();
  }

  private static void deleteSingleTransaction(@NotNull MainView view) {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter ID", InputReader.TEXT));

    String id = InputReader.readString();
    if (Database.db().transactionExists(id)) {
      view.appendWithNewLine("\n" +
          UIFormatter.warningStyle("This operation is not reversible. Do you want to continue?"));

      boolean choice = MenuHandler.handleYesNo(view);

      if (choice) {
        Database.db().deleteTransaction(id);
        view.appendWithNewLine(
            UIFormatter.successStyle("Transaction deleted."));
      }
    } else {
      view.appendWithNewLine(UIFormatter.errorStyle("Invalid or non existent ID."));
    }
  }

  private static void deleteAllTransactions(@NotNull MainView view) {
    view.appendWithNewLine(
        UIFormatter.warningStyle("All the recorded transactions will be deleted."));
    view.appendWithNewLine(
        UIFormatter.warningStyle("This operation is not reversible. Do you want to continue?"));

    boolean choice = MenuHandler.handleYesNo(view);

    if (choice) {
      Database.db().deleteAllTransactions();
      view.appendWithNewLine(
          UIFormatter.successStyle("Transactions deleted."));
    }
  }
}
