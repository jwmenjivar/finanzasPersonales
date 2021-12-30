package com.finanzaspersonales.presenter.input;

import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.InputMismatchException;

public class MenuInput extends SimpleInput {

  protected MenuInput() { }

  /**
   * Displays a menu and handles the input.
   */
  public static String processMenu(@NotNull MenuItem[] menuItems, @NotNull View view) {
    view.append(UIFormatter.menuStyle(menuItems) + "\n");

    String input = "";
    while (input.isEmpty()) {
      input = handleMenu(menuItems, view);
    }

    return input;
  }

  /**
   * Asks the user to answer yes or no to a prompt.
   * @param view Presenter view
   * @return True equals yes
   */
  public static boolean handleYesNo(@NotNull View view) {
    String choice = "";
    while (choice.isEmpty()) {
      try {
        view.append(UIFormatter.promptStyle("Enter choice", "Y/N"));
        choice = readYesOrNo();
      } catch (InputMismatchException e) {
        view.append(UIFormatter.errorStyle(e.getMessage()));
        choice = "";
      }
    }

    return Arrays.asList("Y", "y").contains(choice);
  }

  /**
   * Asks the user to choose an option from the menu items. All the prompts
   * are displayed in the provided view.
   * @param view Presenter view
   * @return Menu item name
   */
  public static String handleMenu(@NotNull MenuItem[] menuItems, @NotNull View view) {
    String item = "";

    try {
      view.append(
          UIFormatter.promptStyle("Enter option", SimpleInput.OPTIONS));
      item = readMenuOption(menuItems);
      view.append(UIFormatter.addNewLine("You have chosen: " + item) + "\n");
    } catch (InputMismatchException exception) {
      view.append(UIFormatter.addNewLine(UIFormatter.errorStyle(exception.getMessage())));
    }

    return item;
  }
}
