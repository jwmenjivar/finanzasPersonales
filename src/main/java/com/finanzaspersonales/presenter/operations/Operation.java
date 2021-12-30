package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.MainView;

import java.io.FileNotFoundException;
import java.io.IOException;

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

  public void operate() {
    startOperation();
    try {
      operation();
    } catch (Exception ex) {
      System.out.println(ex);
    }
    endOperation();
  }

  protected void operation() throws IOException { }

  /**
   * Displays the title and subtitle in the view.
   */
  protected void startOperation() {
    view.appendWithoutNewline(UIFormatter.titleStyle(title));
    view.appendWithoutNewline(UIFormatter.subtitleStyle(subtitle));
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
