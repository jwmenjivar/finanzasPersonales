package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Budget;
import com.finanzaspersonales.model.Budgets;
import com.finanzaspersonales.model.Report;
import com.finanzaspersonales.model.Reports;
import com.finanzaspersonales.presenter.operations.CreateBudget;
import com.finanzaspersonales.presenter.operations.DeleteBudget;
import com.finanzaspersonales.presenter.operations.Operation;
import com.finanzaspersonales.presenter.ui.BudgetFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

public class BudgetPresenter extends Presenter {
  private final CreateBudget createBudget;
  private final DeleteBudget deleteBudget;

  public BudgetPresenter(View view) {
    super(view);
    createBudget = new CreateBudget(view);
    deleteBudget = new DeleteBudget(view);

    menuItems = new MenuItem[]{
        new MenuItem(
            Operation.ENABLE,
            "Enable monthly budget."),
        new MenuItem(
            Operation.DISABLE,
            "Disable monthly budget."),
        new MenuItem(
            "Back",
            "Back to the main menu.")};
  }

  @Override
  protected Action chooseOperation(@NotNull String operation) {
    switch (operation) {
      case Operation.ENABLE -> {
        createBudget.operate();
        return Action.RELOAD;
      }
      case Operation.DISABLE -> {
        deleteBudget.operate();
        return Action.RELOAD;
      }
      case "Back" -> {
        return Action.MENU;
      }
      default -> {
        return Action.NONE;
      }
    }
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Budget");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    if(Budgets.isBudgetSet()) {
      toDisplay += UIFormatter.titleStyle("Budget report");
      Budget budget = Budgets.get();
      Report report = Reports.calculateReport();
      toDisplay += BudgetFormatter.budgetReport(budget, report);
    }

    // MENU
    toDisplay += UIFormatter.titleStyle("Budget menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    this.view.displayContent(toDisplay);
  }
}
