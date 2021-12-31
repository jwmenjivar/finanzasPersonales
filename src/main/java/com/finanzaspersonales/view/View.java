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
  void initialize(String header, String content,
                  String menuTitle, String menuSubtitle, String menu);

  /**
   * Prints the prompt without a new line.
   */
  void append(String prompt);

  /**
   * Prints a prompt with its type using UIFormatter.promptStyle.
   */
  void prompt(String prompt, String type);

  /**
   * Prints a success message using UIFormatter.sucessStyle.
   */
  void success(String success);

  /**
   * Prints a warning message using UIFormatter.warningStyle.
   */
  void warning(String warning);

  /**
   * Prints an error message using UIFormatter.errorStyle.
   */
  void error(String error);

  /**
   * Prints a message and reads an empty string.
   * Use to interrupt the flow before reloading screens or continuing
   * an operation.
   */
  void pressContinue();

  /**
   * Returns a new MainView implementation.
   */
  @NotNull
  @Contract(" -> new")
  static View getView() {
    return new ViewImpl();
  }
}
