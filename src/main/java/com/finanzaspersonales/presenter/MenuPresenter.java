package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.*;
import com.finanzaspersonales.presenter.ui.BudgetFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Acts upon the main menu view.
 * Displays the main menu and handles the user input.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class MenuPresenter extends Presenter {

  public MenuPresenter(View view, String name) {
    super(view, name);
  }

  @Override
  protected void loadView() {
    /* CONTENT */
    String content = "";
    String pattern = "E dd, MMM yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    content += UIFormatter.highlightStyle(
        UIFormatter.center("Today is " + simpleDateFormat.format(new Date())));

    Budget budget = Budgets.get();
    if (budget.isEnabled()) {
      content += UIFormatter.titleStyle("Budget summary");
      Report report = Reports.calculateYearReport(LocalDate.now().getYear());
      content += BudgetFormatter.budgetSummary(budget, report);
    }

    content += UIFormatter.titleStyle("Today's transactions");
    content += TransactionFormatter.transactionsTable(Transactions.getToday()) + "\n";

    view.initialize(
        "My Finance App",
        content,
        "Main menu",
        "Write the number or name of the menu option to navigate to that screen.",
        UIFormatter.menuStyle(menuItems.toArray(MenuItem[]::new)));
  }
}
