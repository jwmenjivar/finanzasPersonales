package com.finanzaspersonales.presenter.operations;


public class Navigation implements Operational {
  private final String presenterName;

  public Navigation(String presenterName) {
    this.presenterName = presenterName;
  }

  @Override
  public String operate() {
    return presenterName;
  }
}