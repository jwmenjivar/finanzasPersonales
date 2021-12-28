package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.InputMismatchException;

public class MenuHandler {

  /**
   * Asks the user to choose an option from the menu items. All the prompts
   * are displayed in the provided view.
   * @param view Presenter view
   * @return Menu item name
   */
  public static String handleMenu(@NotNull MenuItem[] menuItems, @NotNull MainView view) {
    String item = "";

    try {
      view.appendWithoutNewline(
          UIFormatter.promptStyle("Enter option", InputReader.OPTIONS));
      item = InputReader.readMenuOption(menuItems);
      view.appendWithNewLine(UIFormatter.addNewLine("You have chosen: " + item));
    } catch (InputMismatchException exception) {
      view.appendWithNewLine("\n" +
          UIFormatter.errorStyle(exception.getMessage()));
    }

    return item;
  }

  public static boolean handleYesNo(@NotNull MainView view) {
    String choice = "";
    while (choice.isEmpty()) {
      try {
        view.appendWithoutNewline(UIFormatter.promptStyle("Enter choice", "Y/N"));
        choice = InputReader.readYesOrNo();
      } catch (InputMismatchException e) {
        view.appendWithNewLine(UIFormatter.errorStyle(e.getMessage()));
        choice = "";
      }
    }

    return Arrays.asList("Y", "y").contains(choice);
  }
}
