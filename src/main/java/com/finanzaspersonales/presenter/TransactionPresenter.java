package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.operations.*;
import com.finanzaspersonales.presenter.operations.transaction.CreateTransaction;
import com.finanzaspersonales.presenter.operations.transaction.DeleteTransaction;
import com.finanzaspersonales.presenter.operations.transaction.ShowTransactions;
import com.finanzaspersonales.presenter.operations.transaction.UpdateTransaction;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

public class TransactionPresenter extends Presenter {
  private final CreateTransaction createTransaction;
  private final UpdateTransaction updateTransaction;
  private final DeleteTransaction deleteTransaction;
  private final ShowTransactions showTransactions;
  private final ExportOperation exportOperation;

  public TransactionPresenter(View view) {
    super(view);
    createTransaction = new CreateTransaction(this.view);
    updateTransaction = new UpdateTransaction(this.view);
    deleteTransaction = new DeleteTransaction(this.view);
    showTransactions = new ShowTransactions(this.view);
    exportOperation = new ExportOperation(this.view);

    menuItems = new MenuItem[]{
        new MenuItem(
            Operation.CREATE,
            "Create a new transaction."),
        new MenuItem(
            Operation.SHOW,
            "Show recorded transactions."),
        new MenuItem(
            Operation.UPDATE,
            "Update recorded transactions."),
        new MenuItem(
            Operation.DELETE,
            "Delete recorded transactions."),
        new MenuItem(
            Operation.EXPORT,
            "Export transactions."),
        new MenuItem(
            "Back",
            "Back to the main menu.")};
  }

  @Override
  protected Action chooseOperation(@NotNull String operation) {
    switch (operation) {
      case Operation.CREATE -> {
        createTransaction.operate();
        return Action.RELOAD;
      }
      case Operation.SHOW -> {
        showTransactions.operate();
        return Action.RELOAD;
      }
      case Operation.UPDATE -> {
        updateTransaction.operate();
        return Action.RELOAD;
      }
      case Operation.DELETE -> {
        deleteTransaction.operate();
        return Action.RELOAD;
      }
      case Operation.EXPORT -> {
        exportOperation.operate();
        return Action.RELOAD;
      }
      default -> {
        return Action.MENU;
      }
    }
  }

  @Override
  protected void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Transactions");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    toDisplay += UIFormatter.titleStyle("Transactions menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    this.view.displayContent(toDisplay);
  }
}
