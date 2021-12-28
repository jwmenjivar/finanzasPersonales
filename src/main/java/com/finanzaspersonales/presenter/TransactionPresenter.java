package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.MainView;
import com.finanzaspersonales.view.TransactionView;

import java.util.List;

public class TransactionPresenter extends Presenter {
  private final TransactionView transactionView;

  public TransactionPresenter(TransactionView transactionView) {
    this.transactionView = transactionView;
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
            "Create",
            "Create a new transaction."),
        new MenuItem(
            "Show",
            "Show recorded transactions."),
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
  public Action handleInput() {
    String menuOption = MenuHandler.handleMenuOption(
        this.menuItems, this.transactionView);
    Action action = new Action();

    switch (menuOption) {
      case "Create" -> {
        CreateTransaction.create(this.transactionView);

        action.actionType = Action.ActionType.NAVIGATION;
        action.nextView = this.transactionView;

        return action;
      }
      case "Show" -> {
        ShowTransactions.showAll(this.transactionView);

        action.actionType = Action.ActionType.NAVIGATION;
        action.nextView = this.transactionView;

        return action;
      }
      case "Back" -> {
        action.actionType = Action.ActionType.NAVIGATION;
        action.nextView = MainView.getMainView();
        return action;
      }
      default -> {
        return action;
      }
    }
  }
}
