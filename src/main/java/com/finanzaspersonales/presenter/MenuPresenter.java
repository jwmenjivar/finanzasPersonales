package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Budgets;
import com.finanzaspersonales.model.Reports;
import com.finanzaspersonales.model.Transactions;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.BudgetFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
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
    switch (operation) {
      case "Transactions" -> { return Action.TRANSACTION; }
      case "Categories" -> { return Action.CATEGORY; }
      case "Budget" -> { return Action.BUDGET; }
      case "Reports" -> { return Action.REPORT; }
      case "Help" -> {
        view.append(
            UIFormatter.wrapText("This is supposed to be the help."));
        view.append(
            UIFormatter.confirmationPromptStyle("Press ENTER to continue")
        );
        SimpleInput.readString();
        return Action.RELOAD;
      }
      case "Exit" -> {
        view.append(UIFormatter.highlightStyle("Goodbye."));
        return Action.EXIT;
      }
      default -> { return Action.NONE; }
    }
  }

  @Override
  protected void loadView() {
    String toDisplay = "";

    /* HEADER */
    toDisplay += UIFormatter.headerStyle("My Finance App");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    /* CONTENT */
    String pattern = "E dd, MMM yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    toDisplay += UIFormatter.addNewLine(UIFormatter.highlightStyle(
        UIFormatter.center("Today is " + simpleDateFormat.format(new Date()))));

    if (Budgets.isBudgetSet()) {
      toDisplay += UIFormatter.titleStyle("Budget summary");
      toDisplay += BudgetFormatter.budgetSummary(Budgets.get(), Reports.calculateReport());
    }

    toDisplay += UIFormatter.titleStyle("Today's transactions");
    toDisplay += TransactionFormatter
        .transactionsTable(Transactions.getToday());
    toDisplay = UIFormatter.addNewLine(toDisplay);

    /* MENU */
    // MAYBE: Load the items from a file
    toDisplay += UIFormatter.titleStyle("Main menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    /* DISPLAY VIEW */
    view.displayContent(toDisplay);
  }
}
