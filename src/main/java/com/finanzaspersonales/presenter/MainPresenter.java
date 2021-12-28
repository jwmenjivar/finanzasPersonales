package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Acts upon the main menu view.
 * Displays the main menu and handles the user input.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class MainPresenter extends Presenter {
  private final MainView mainView;

  /**
   * Creates a MainPresenter with its designated view
   * @param mainView
   */
  public MainPresenter(MainView mainView) {
    this.mainView = mainView;
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    /* HEADER */
    toDisplay += UIFormatter.headerStyle("My Finance App");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    /* CONTENT */
    String pattern = "E dd, MMM yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    toDisplay += UIFormatter.highlightStyle(
        UIFormatter.center("Today is " + simpleDateFormat.format(new Date())));

    // TODO: retrieve the top 10 transactions with date of today
    toDisplay += UIFormatter.titleStyle("Today's transactions");
    toDisplay += TransactionFormatter.transactionsTable(new ArrayList<>());
    toDisplay = UIFormatter.addNewLine(toDisplay);

    /* MENU */
    // MAYBE: Load the items from a file
    this.menuItems = List.of(
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
        ));
    toDisplay += UIFormatter.titleStyle("Main menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menu(menuItems);

    /* DISPLAY VIEW */
    mainView.displayContent(toDisplay);
  }

  /**
   * Asks the user to choose a menu option and returns an appropriate
   * action to the application.
   * @return Action for the app to handle
   */
  @Override
  public Action handleInput() {
    String menuOption = handleMenuOption(this.mainView);

    switch (menuOption) {
      case "Transactions" -> {
        return new Action(
            Action.ActionType.NAVIGATION,
            TransactionView.getTransactionView());
      }
      case "Categories" -> {
        return new Action(
            Action.ActionType.NAVIGATION,
            CategoryView.getCategoryView());
      }
      case "Budget" -> {
        return new Action(
              Action.ActionType.NAVIGATION,
              BudgetView.getBudgetView());
      }
      case "Reports" -> {
        return new Action(
            Action.ActionType.NAVIGATION,
            ReportView.getReportView());
      }
      case "Help" -> {
        this.mainView.appendContent(
            UIFormatter.wrapText("This is supposed to be the help."));
        return new Action(
            Action.ActionType.NONE);
      }
      case "Exit" -> {
        this.mainView.appendContent(UIFormatter.highlightStyle("Goodbye."));
        return new Action(
            Action.ActionType.EXIT);
      }
      default -> {
        return new Action(Action.ActionType.NONE);
      }
    }
  }
}
