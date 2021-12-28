package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.MainPresenter;
import com.finanzaspersonales.presenter.Presenter;
import lombok.Getter;

/**
 * Implements the MainView.
 * Displays the main menu.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
class MainViewImpl implements MainView {
  @Getter
  protected Presenter presenter;

  public MainViewImpl() {
    this.presenter = new MainPresenter(this);
  }

  public void initialize() {
    presenter.loadView();
  }

  public void displayContent(String content) {
    clearScreen();
    System.out.println(content);
  }

  public void showPrompt(String prompt) {
    System.out.print(prompt);
  }

  public void appendContent(String content) {
    System.out.println(content);
  }

  /**
   * Clears the terminal screen.
   */
  protected void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
