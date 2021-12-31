package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;

/**
 * Implements the MainView.
 * Displays the main menu.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
class ViewImpl implements View {

  public void initialize(String header, String content,
                         String menuTitle, String menuSubtitle, String menu) {
    clearScreen();
    append(UIFormatter.headerStyle(header) + "\n");
    append(content);
    append(UIFormatter.titleStyle(menuTitle));
    append(UIFormatter.subtitleStyle(menuSubtitle));
    append(menu + "\n");
  }

  public void append(String content) {
    System.out.print(content);
  }

  public void prompt(String prompt, String type) {
    append(UIFormatter.promptStyle(prompt, type));
  }

  public void success(String success) {
    append(UIFormatter.successStyle(success) + "\n");
  }

  public void warning(String warning) {
    append("\n" + UIFormatter.warningStyle(warning) + "\n");
  }

  public void error(String error) {
    append(UIFormatter.errorStyle(error) + "\n");
  }

  public void pressContinue() {
    append(UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    SimpleInput.readString();
  }

  /**
   * Clears the terminal screen.
   */
  protected void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
