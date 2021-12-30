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
   */
  void displayContent(String content);

  /**
   * Prints the prompt without a new line.
   */
  void append(String prompt);

  /**
   * Returns a new MainView implementation.
   */
  @NotNull
  @Contract(" -> new")
  static View getView() {
    return new ViewImpl();
  }
}
