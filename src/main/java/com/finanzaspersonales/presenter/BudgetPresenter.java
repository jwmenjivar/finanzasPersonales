package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.data.Budget;
import com.finanzaspersonales.model.db.Budgets;
import com.finanzaspersonales.model.data.Report;
import com.finanzaspersonales.model.db.Reports;
import com.finanzaspersonales.presenter.ui.BudgetFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

import java.time.LocalDate;

public class BudgetPresenter extends Presenter {

  public BudgetPresenter(View view, String name) {
    super(view, name);
  }

  @Override
  public void loadView() {
    // CONTENT
    String content = "";
    Budget budget = Budgets.get();
    if(budget.isEnabled()) {
      Report report = Reports.calculateYearReport(LocalDate.now().getYear());
      content += UIFormatter.titleStyle("Budget report");
      content += BudgetFormatter.budgetReport(budget, report);
    }

    view.initialize(
        "Budget",
        content,
        "Budget menu",
        "Write the number or name of the menu option to navigate to that screen.",
        UIFormatter.menuStyle(menuItems.toArray(MenuItem[]::new)));
  }
}
