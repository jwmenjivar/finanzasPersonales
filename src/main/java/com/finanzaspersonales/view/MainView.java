package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.MainPresenter;

public class MainView extends AbstractView {

  public MainView() {
    this.presenter = new MainPresenter(this);
  }
}
