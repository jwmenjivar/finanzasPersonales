package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.operations.*;
import com.finanzaspersonales.presenter.operations.transaction.CreateTransaction;
import com.finanzaspersonales.presenter.operations.transaction.DeleteTransaction;
import com.finanzaspersonales.presenter.operations.transaction.ShowTransactions;
import com.finanzaspersonales.presenter.operations.transaction.UpdateTransaction;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;
import com.finanzaspersonales.view.TransactionView;

public class TransactionPresenter extends Presenter {
  private final TransactionView transactionView;
  private final CreateTransaction createTransaction;
  private final UpdateTransaction updateTransaction;
  private final DeleteTransaction deleteTransaction;
  private final ShowTransactions showTransactions;
  private final ExportOperation exportOperation;

  public TransactionPresenter(TransactionView transactionView) {
    this.transactionView = transactionView;
    createTransaction = new CreateTransaction(this.transactionView);
    updateTransaction = new UpdateTransaction(this.transactionView);
    deleteTransaction = new DeleteTransaction(this.transactionView);
    showTransactions = new ShowTransactions(this.transactionView);
    exportOperation = new ExportOperation(this.transactionView);
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Transactions");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    this.menuItems = new MenuItem[]{
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
            "Export",
            "Export transactions."),
        new MenuItem(
            "Back",
            "Back to the main menu.")};
    toDisplay += UIFormatter.titleStyle("Transactions menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    this.transactionView.displayContent(toDisplay);
  }

  @Override
  public Action chooseOperation() {
    String menuOption = MenuInput.handleMenu(
        this.menuItems, this.transactionView);
    Action action = new Action();

    switch (menuOption) {
      case Operation.CREATE -> {
        createTransaction.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case Operation.SHOW -> {
        showTransactions.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case Operation.UPDATE -> {
        updateTransaction.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case Operation.DELETE -> {
        deleteTransaction.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case "Export" -> {
        exportOperation.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case "Back" -> {
        action.setActionType(Action.ActionType.NAVIGATION);
        action.setNextView(MainView.getMainView());
        return action;
      }
      default -> {
        return action;
      }
    }
  }
}
