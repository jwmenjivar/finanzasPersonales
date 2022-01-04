package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

public record ExitApp(View view) implements Operational {

  @Override
  public String operate() {
    view.append(UIFormatter.highlightStyle("Goodbye."));
    return "EXIT";
  }
}
