package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.CategoryView;
import com.finanzaspersonales.view.MainView;

import java.util.List;

public class CategoryPresenter extends Presenter {
  private final CategoryView categoryView;

  public CategoryPresenter(CategoryView categoryView) {
    this.categoryView = categoryView;
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Categories");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    this.menuItems = List.of(
        new MenuItem(
            "Back",
            "Back to the main menu."));
    toDisplay += UIFormatter.titleStyle("Categories menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menu(menuItems);

    this.categoryView.displayContent(toDisplay);
  }

  @Override
  public Action handleInput() {
    String menuOption = handleMenuOption(this.categoryView);
    Action action = new Action();

    // return to the main view automatically
    // TODO: show all the categories options
    switch (menuOption) {
      case "Back":
        action.actionType = Action.ActionType.NAVIGATION;
        action.nextView = MainView.getMainView();
        break;
      default:
        action.actionType = Action.ActionType.NONE;
        break;
    }

    return action;
  }
}
