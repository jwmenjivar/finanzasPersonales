package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.*;
import com.finanzaspersonales.presenter.ui.BudgetFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

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

  /**
   * Creates a MainPresenter with its designated view
   */
  public MenuPresenter(View view) {
    super(view);
    menuItems = new MenuItem[]{
        new MenuItem(
            "Transactions",
            "Create, update, search, and delete transactions."),
        new MenuItem(
            "Categories",
            "Create, update, and delete categories."),
        new MenuItem(
            "Budget",
            "Set monthly and category budgets."),
        new MenuItem(
            "Reports",
            "Create monthly, yearly, and category reports to export or email."),
        new MenuItem(
            "Help",
            "User manual."),
        new MenuItem(
            "Exit",
            "Close application."
        )};
  }

  /**
   * Asks the user to choose a menu option and returns an appropriate
   * action to the application.
   * @return Action for the app to handle
   */
  @Override
  protected Action chooseOperation(@NotNull String operation) {
    return switch (operation) {
      case "Transactions" -> Action.TRANSACTION;
      case "Categories" -> Action.CATEGORY;
      case "Budget" -> Action.BUDGET;
      case "Reports" -> Action.REPORT;
      case "Help" -> {
        view.append(
            UIFormatter.wrapText("This is supposed to be the help."));
        view.pressContinue();
        yield Action.RELOAD;
      }
      case "Exit" -> {
        view.append(UIFormatter.highlightStyle("Goodbye."));
        yield Action.EXIT;
      }
      default -> Action.NONE;
    };
  }

  @Override
  protected void loadView() {

    /* CONTENT */
    String content = "";
    String pattern = "E dd, MMM yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    content += UIFormatter.highlightStyle(
        UIFormatter.center("Today is " + simpleDateFormat.format(new Date()))) + "\n";

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
        UIFormatter.menuStyle(menuItems));
  }
}
