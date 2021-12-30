package com.finanzaspersonales.view;

/**
 * Implements the MainView.
 * Displays the main menu.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
class ViewImpl implements View {

  public void displayContent(String content) {
    clearScreen();
    System.out.println(content);
  }

  public void append(String content) {
    System.out.print(content);
  }

  /**
   * Clears the terminal screen.
   */
  protected void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
