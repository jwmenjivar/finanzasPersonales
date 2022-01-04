package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

/**
 * Represents a CRUD operation.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public abstract class Operation implements Operational {
  protected View view;
  protected final String title;
  protected final String subtitle;

  protected Operation(View view, String title, String subtitle) {
    this.view = view;
    this.title = title;
    this.subtitle = subtitle;
  }

  public String operate() {
    startOperation();
    operation();
    view.pressContinue();

    return "RELOAD";
  }

  protected void operation() { }

  /**
   * Displays the title and subtitle in the view.
   */
  protected void startOperation() {
    view.append(UIFormatter.titleStyle(title));
    view.append(UIFormatter.subtitleStyle(subtitle));
  }
}
