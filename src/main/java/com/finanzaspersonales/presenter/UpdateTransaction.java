package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

public class UpdateTransaction extends TransactionInput {

  private UpdateTransaction() {
    super();
  }

  public static void update(@NotNull MainView view) {
    view.appendWithoutNewline(UIFormatter.titleStyle("Updating a transaction"));
    view.appendWithoutNewline(UIFormatter.subtitleStyle("Enter a valid ID: "));

    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter ID", SimpleInput.TEXT));

    String id = SimpleInput.readString();
    if (Database.db().transactionExists(id)) {
      Transaction t = Database.db().getTransactionByID(id);
      view.appendWithNewline("\n" + TransactionFormatter.transactionDetailed(t));

      view.appendWithoutNewline(UIFormatter.subtitleStyle("Choose what to edit: "));
      MenuItem[] menuItems = new MenuItem[]{
          new MenuItem("Amount"),
          new MenuItem("Date"),
          new MenuItem("Category"),
          new MenuItem("Description"),
          new MenuItem("Back")
      };
      view.appendWithNewline(UIFormatter.menuStyle(menuItems));

      String input = "";
      while (input.isEmpty()) {
        input = MenuInput.handleMenu(menuItems, view);
      }

      switch (input) {
        case "Amount" -> assignAmount(view, t);
        case "Date" -> assignDate(view, t);
        case "Category" -> chooseCategory(view, t);
        case "Description" -> assignDescription(view, t);
        default -> { /* go back */ }
      }

      if (!input.equals("Back")) {
        view.appendWithNewline(
            UIFormatter.successStyle("Transaction updated."));
        view.appendWithoutNewline("\n" + TransactionFormatter.transactionDetailed(t));
      }
    } else {
      view.appendWithNewline(UIFormatter.errorStyle("Invalid or non existent ID."));
    }

    view.appendWithoutNewline(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    SimpleInput.readString();
  }
}
