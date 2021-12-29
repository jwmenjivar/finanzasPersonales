package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.operations.CreateBudget;
import com.finanzaspersonales.presenter.operations.Operation;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.BudgetView;
import com.finanzaspersonales.view.MainView;

public class BudgetPresenter extends Presenter {
  private final BudgetView budgetView;
  private final CreateBudget createBudget;

  public BudgetPresenter(BudgetView budgetView) {
    this.budgetView = budgetView;
    createBudget = new CreateBudget(budgetView);
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
            Operation.CREATE,
            "Create budget."),
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

    switch (menuOption) {
      case Operation.CREATE -> {
        createBudget.create();
        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case "Back" -> {
        action.setActionType(Action.ActionType.NAVIGATION);
        action.setNextView(MainView.getMainView());
      }
      default -> action.setActionType(Action.ActionType.NONE);
    }

    return action;
  }
}
