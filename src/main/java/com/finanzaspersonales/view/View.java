package com.finanzaspersonales.view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for the Main View. Exposes the behavior for the Presenter.
 * Serves as the base interface for the other views.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public interface View {
  /**
   * Prints the content on screen with a new line.
   * @param content
   */
  void displayContent(String content);

  /**
   * Prints the prompt without a new line.
   * @param prompt
   */
  void appendWithoutNewline(String prompt);

  /**
   * Adds new content to the screen with a new line.
   * @param content
   */
  void appendWithNewline(String content);

  /**
   * Returns a new MainView implementation.
   * @return
   */
  @NotNull
  @Contract(" -> new")
  static View getView() {
    return new ViewImpl();
  }
}
