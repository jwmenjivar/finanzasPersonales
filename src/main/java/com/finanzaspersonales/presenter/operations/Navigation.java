package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.Action;
import com.finanzaspersonales.ActionType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Navigation(String presenterName) implements Operational {

  @Override
  @NotNull
  @Contract(" -> new")
  public Action operate() {
    return new Action(ActionType.NAVIGATE, presenterName);
  }
}