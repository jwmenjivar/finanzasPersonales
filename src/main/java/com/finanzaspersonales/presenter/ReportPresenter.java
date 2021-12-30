package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

public class ReportPresenter extends Presenter {

  public ReportPresenter(View view) {
    super(view);
    menuItems = new MenuItem[]{
        new MenuItem(
            "Back",
            "Back to the main menu.")};
  }

  @Override
  protected Action chooseOperation(String operation) {
    // return to the main view automatically
    // TODO: show all the reports options
    return Action.MENU;
  }

  @Override
  protected void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Reports");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    toDisplay += UIFormatter.titleStyle("Reports menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    this.view.displayContent(toDisplay);
  }
}
