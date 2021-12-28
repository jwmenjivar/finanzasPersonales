package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.MainView;

/**
 * Represents a transaction CRUD operation.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public abstract class Operation {
  public static final String CREATE = "Create";
  public static final String UPDATE = "Update";
  public static final String SHOW = "Show";
  public static final String DELETE = "Delete";

  protected MainView view;
  protected final String title;
  protected final String subtitle;

  protected Operation(MainView view, String title, String subtitle) {
    this.view = view;
    this.title = title;
    this.subtitle = subtitle;
  }

  /**
   * Displays the title and subtitle in the view.
   */
  protected void startOperation() {
    view.appendWithoutNewline(UIFormatter.titleStyle(title));
    view.appendWithoutNewline(UIFormatter.subtitleStyle(subtitle));
  }

  /**
   * Displays a menu and handles the input.
   */
  protected String processMenu(MenuItem[] items) {
    view.appendWithNewline(UIFormatter.menuStyle(items));

    String input = "";
    while (input.isEmpty()) {
      input = MenuInput.handleMenu(items, view);
    }

    return input;
  }

  /**
   * Ends operation with a confirmation prompt.
   */
  protected void endOperation() {
    view.appendWithoutNewline(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    SimpleInput.readString();
  }
}