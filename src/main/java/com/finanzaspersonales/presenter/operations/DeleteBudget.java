package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Budgets;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.view.View;

public class DeleteBudget extends Procedure {

  public DeleteBudget(View view) {
    super(view, "Disable the budget", "Disabling the budget sets the amount to 0: ");
  }

  @Override
  protected void operation() {
    view.warning("Are you sure you want to disable the monthly budget?");
    boolean choice = MenuInput.handleYesNo(view);

    if (choice) {
      Budgets.disable();
      view.success("Budget disabled.");
    }
  }
}

