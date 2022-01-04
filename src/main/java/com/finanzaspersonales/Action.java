package com.finanzaspersonales;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Action {
  @NotNull private ActionType type;
  private String navigateTo;
}
