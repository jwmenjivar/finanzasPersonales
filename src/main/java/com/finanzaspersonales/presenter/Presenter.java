package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.operations.Operational;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.view.View;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
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
  @Getter private final String name;
  protected final View view;
  protected List<MenuItem> menuItems;
  protected HashMap<String, Operational> operations;

  protected Presenter(View view, String name) {
    this.view = view;
    this.name = name;
    operations = new HashMap<>();
    menuItems = new ArrayList<>();
  }

  /**
   * Asks for the user input, validates it, and returns an appropriate action.
   * @return Tells the app what to do next.
   */
  public String present() {
    loadView();
    String to = "";
    while (to.isEmpty()) {
      String menuOption = MenuInput.handleMenu(menuItems.toArray(MenuItem[]::new), view);
      to = chooseOperation(menuOption);
    }
    return to;
  }

  public void addOperational(@NotNull String name, @NotNull Operational operation,
                             String description) {
    operations.put(name, operation);
    menuItems.add(new MenuItem(name, description));
  }

  protected String chooseOperation(@NotNull String operation) {
    String action;
    try {
      action = operations.get(operation).operate();
    } catch (IllegalArgumentException e) {
      action = "MENU";
    }

    return action;
  }

  /**
   * Creates all the ui elements, formats it and displays it in the view.
   * Calls the View.initialize method to print the presenter's main menu.
   */
  protected void loadView() {}
}
