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
    this.menuItems = List.of(
        new MenuItem(
            "Back",
            "Back to the main menu."));
    toDisplay += UIFormatter.titleStyle("Transactions menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menu(menuItems);

    this.transactionView.displayContent(toDisplay);
  }

  @Override
  public Action handleInput() {
    String menuOption = handleMenuOption(this.transactionView);
    Action action = new Action();

    // return to the main view automatically
    // TODO: show all the transaction options
    switch (menuOption) {
      case "Back":
        action.actionType = Action.ActionType.NAVIGATION;
        action.nextView = MainView.getMainView();
        break;
      default:
        action.actionType = Action.ActionType.NONE;
        break;
    }

    return action;
  }
}
