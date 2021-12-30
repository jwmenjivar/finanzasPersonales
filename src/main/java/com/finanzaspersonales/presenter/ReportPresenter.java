package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;
import com.finanzaspersonales.view.ReportView;

public class ReportPresenter extends Presenter {
  private final ReportView reportView;

  public ReportPresenter(ReportView reportView) {
    this.reportView = reportView;
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Reports");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    this.menuItems = new MenuItem[]{
        new MenuItem(
            "Back",
            "Back to the main menu.")};
    toDisplay += UIFormatter.titleStyle("Reports menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    this.reportView.displayContent(toDisplay);
  }

  @Override
  public Action chooseOperation() {
    String menuOption = MenuInput.handleMenu(
        this.menuItems, this.reportView);
    Action action = new Action();

    // return to the main view automatically
    // TODO: show all the reports options
    switch (menuOption) {
      case "Back":
        action.setActionType(Action.ActionType.NAVIGATION);
        action.setNextView(MainView.getMainView());
        break;
      default:
        action.setActionType(Action.ActionType.NONE);
        break;
    }

    return action;
  }
}
