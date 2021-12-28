package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.InputMismatchException;
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
  protected MenuItem[] menuItems;

  /**
   * Creates all the ui elements, formats it and displays it in the view.
   */
  public void loadView() {}

  /**
   * Asks for the user input, validates it, and returns an appropriate action.
   * @return Tells the app what to do next.
   */
  public Action handleInput() {
    Action a = new Action();
    a.actionType = Action.ActionType.NONE;
    return a;
  }
}
