package com.finanzaspersonales.presenter;

import com.finanzaspersonales.Action;
import com.finanzaspersonales.ActionType;
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
  public Action present() {
    loadView();
    Action action = new Action(ActionType.NONE);
    while (action.getType() == ActionType.NONE) {
      String menuOption = MenuInput.handleMenu(menuItems.toArray(MenuItem[]::new), view);
      action = runOperation(menuOption);
    }
    return action;
  }


  //With addOperational
  // we put to the hashmap operations
          /*
          The name we got from main line 46 of main ooc
          and an Operational object type
           */
  //we add to the menuItems ArrayList
          /*
            Se le pasa la construccion de un MenuItem con arguments name, and description
           */
  public void addOperational(@NotNull String name, @NotNull Operational operation,
                             String description) {
    operations.put(name, operation);
    menuItems.add(new MenuItem(name, description));
  }






  protected Action runOperation(@NotNull String operation) {
    Action action;
    try {
      action = operations.get(operation).operate();
    } catch (IllegalArgumentException e) {
      action = new Action(ActionType.NAVIGATE, "MENU");
    }

    return action;
  }

  /**
   * Creates all the ui elements, formats it and displays it in the view.
   * Calls the View.initialize method to print the presenter's main menu.
   */
  protected void loadView() {}
}
