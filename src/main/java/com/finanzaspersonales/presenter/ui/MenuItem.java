package com.finanzaspersonales.presenter.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class MenuItem {
  @NotNull
  private String item;
  private String description;
}
