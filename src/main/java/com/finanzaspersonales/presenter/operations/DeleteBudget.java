package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Budgets;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

public class DeleteBudget extends Operation {

  public DeleteBudget(View view) {
    super(view, "Disable the budget", "Disabling the budget sets the amount to 0: ");
  }

  @Override
  protected void operation() {
    view.append("\n" +
        UIFormatter.warningStyle("Are you sure you want to disable the monthly budget?\n"));
    boolean choice = MenuInput.handleYesNo(view);

    if (choice) {
      Budgets.remove();
      view.append(
          UIFormatter.successStyle("Budget disabled."));
    }
  }
}

