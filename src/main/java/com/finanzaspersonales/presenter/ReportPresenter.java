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
    view.initialize(
        "Reports",
        "",
        "Reports menu",
        "Write the number or name of the menu option to navigate to that screen.",
        UIFormatter.menuStyle(menuItems));
  }
}
