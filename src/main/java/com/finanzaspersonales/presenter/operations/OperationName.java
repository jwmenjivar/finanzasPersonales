package com.finanzaspersonales.presenter.operations;

import lombok.Getter;

public enum OperationName {
  CREATE("Create"), UPDATE("Update"), SHOW("Show"), DELETE("Delete"),
  EXPORT("Export"), BACK("Back");

  //when getName() is called from main, it will refer to what the
  //contstructor built
  @Getter
  private final String name;

  //constructor
  OperationName(String name) { this.name = name; }


}
