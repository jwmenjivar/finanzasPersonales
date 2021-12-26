package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.Presenter;

public abstract class AbstractView implements View {
  protected Presenter presenter;
  private String content;

  public void display(String content) {
    this.content = content;
    System.out.println(this.content);
  }

  public String input(String prompt) {
    // TODO: implement input handler and use prompt to ask for input
    return "";
  }

  public void initialize() {
    clearScreen();
    presenter.loadView();
  }

  private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}
