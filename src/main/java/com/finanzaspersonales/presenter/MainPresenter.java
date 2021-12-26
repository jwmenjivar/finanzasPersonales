package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.MainView;
import com.finanzaspersonales.view.MenuItem;
import com.finanzaspersonales.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPresenter extends Presenter {
  private View mainView;

  public MainPresenter(MainView mainScreen) {
    this.mainView = mainScreen;
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("My Finance App");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // CONTENT
    String pattern = "E dd, MMM yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    toDisplay += UIFormatter.highlightStyle(
        UIFormatter.center("Today is " + simpleDateFormat.format(new Date())));

    toDisplay += UIFormatter.titleStyle("Today's transactions");
    toDisplay += TransactionFormatter.transactionsTable(new ArrayList<>());
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    toDisplay += UIFormatter.titleStyle("Main menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number of the menu option to navigate to that screen.");
    toDisplay +=
        UIFormatter.menu(
            List.of(
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
                ))
        );

    // MESSAGES


    // DISPLAY SCREEN
    mainView.display(toDisplay);
    mainView.input(">");
  }
}
