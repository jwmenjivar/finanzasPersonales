package com.finanzaspersonales.presenter.operations;

import lombok.Getter;

public enum OperationName {
  CREATE("Create"), UPDATE("Update"), SHOW("Show"), DELETE("Delete"),
  EXPORT("Export"), BACK("Back");

  @Getter private final String name;
  OperationName(String name) { this.name = name; }
}
