package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.MainView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Action {
  public enum ActionType {
    NAVIGATION, EXIT, NONE
  }

  @NotNull
  public ActionType actionType;
  public MainView nextView;

  public Action() {
    this.actionType = Action.ActionType.NONE;
  }
}