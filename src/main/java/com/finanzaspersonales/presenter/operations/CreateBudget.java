package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Budget;
import com.finanzaspersonales.model.Budgets;
import com.finanzaspersonales.presenter.input.DataInput;
import com.finanzaspersonales.presenter.ui.BudgetFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

public class CreateBudget extends Operation {

  public CreateBudget(View view) {
    super(view, "Setting the budget", "Input the monthly total: ");
  }

  @Override
  protected void operation() {
    double amount = DataInput.inputAmount(view);
    Budget budget = Budgets.enable(amount);

    showResult(budget);
  }

  /**
   * Shows the transaction after applying the operation.
   */
  protected void showResult(Budget budget) {
    view.success("Budget set.");
    view.append("\n" +
        UIFormatter.highlightStyle("Budget:"));
    view.append("\n" +BudgetFormatter.budgetDetailed(budget));
  }
}
