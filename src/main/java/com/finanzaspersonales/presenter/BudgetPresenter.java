package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.BudgetView;
import com.finanzaspersonales.view.MainView;

public class BudgetPresenter extends Presenter {
  private final BudgetView budgetView;

  public BudgetPresenter(BudgetView budgetView) {
    this.budgetView = budgetView;
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Budget");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    this.menuItems = new MenuItem[]{
        new MenuItem(
            "Back",
            "Back to the main menu.")};
    toDisplay += UIFormatter.titleStyle("Budget menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    this.budgetView.displayContent(toDisplay);
  }

  @Override
  public Action chooseOperation() {
    String menuOption = MenuInput.handleMenu(
        this.menuItems, this.budgetView);
    Action action = new Action();

    // return to the main view automatically
    // TODO: show all the budget options
    switch (menuOption) {
      case "Back":
        action.setActionType(Action.ActionType.NAVIGATION);
        action.setNextView(MainView.getMainView());
        break;
      default:
        action.setActionType(Action.ActionType.NONE);
        break;
    }

    return action;
  }
}
