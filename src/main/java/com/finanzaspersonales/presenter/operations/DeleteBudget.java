package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Budget;
import com.finanzaspersonales.model.Budgets;
import com.finanzaspersonales.presenter.input.DataInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

public class DeleteBudget extends Operation {

  public DeleteBudget(MainView view) {
    super(view, "Disable the budget", "Disabling the budget sets the amount to 0: ");
  }

  @Override
  protected void operation() {
    view.appendWithNewline("\n" +
        UIFormatter.warningStyle("Are you sure you want to disable the monthly budget?"));
    boolean choice = MenuInput.handleYesNo(view);

    if (choice) {
      Budgets.remove();
      view.appendWithNewline(
          UIFormatter.successStyle("Budget disabled."));
    }
  }
}

