package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.MainView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Action {
  public enum ActionType {
    NAVIGATION, RELOAD, EXIT, NONE
  }

  @NotNull
  private ActionType actionType;
  private MainView nextView;

  public Action() {
    this.actionType = Action.ActionType.NONE;
  }
}