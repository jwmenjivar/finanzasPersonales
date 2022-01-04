package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.Action;
import com.finanzaspersonales.ActionType;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record ExitApp(View view) implements Operational {

  @Override
  @NotNull
  @Contract(" -> new")
  public Action operate() {
    view.append(UIFormatter.highlightStyle("Goodbye."));
    return new Action(ActionType.EXIT);
  }
}
