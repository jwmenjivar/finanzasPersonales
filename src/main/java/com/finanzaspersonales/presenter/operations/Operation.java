package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.View;

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
  public static final String ENABLE = "Enable";
  public static final String DISABLE = "Disable";
  public static final String EXPORT = "Export";

  protected View view;
  protected final String title;
  protected final String subtitle;

  protected Operation(View view, String title, String subtitle) {
    this.view = view;
    this.title = title;
    this.subtitle = subtitle;
  }

  public void operate() {
    startOperation();
    operation();
    endOperation();
  }

  protected void operation() { }

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
