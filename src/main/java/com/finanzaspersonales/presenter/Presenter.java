package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * Acts upon the model and the view.
 * Formats the data for display and handles the user input.
 * Controls one view and determines what it displays.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public abstract class Presenter {
  protected List<MenuItem> menuItems;

  /**
   * Creates all the ui elements, formats it and displays it in the view.
   */
  public void loadView() {}

  /**
   * Asks for the user input, validates it, and returns an appropriate action.
   * @return Tells the app what to do next.
   */
  public Action handleInput() {
    Action a = new Action();
    a.actionType = Action.ActionType.NONE;
    return a;
  }

  /**
   * Asks the user to choose an option from the menu items. All the prompts
   * are displayed in the provided view.
   * @param view Presenter view
   * @return Menu item name
   */
  protected String handleMenuOption(@NotNull MainView view) {
    String item = "";

    try {
      view.showPrompt(
          UIFormatter.promptStyle("Enter option", "[number/name]", "$>"));
      item = InputHandler.readMenuOption(this.menuItems);
      view.appendContent("You have chosen: " + item);
    } catch (IOException exception) {
      view.appendContent(exception.getMessage());
    }

    return item;
  }
}
