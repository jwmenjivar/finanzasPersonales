package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.BudgetPresenter;

class BudgetViewImpl extends MainViewImpl implements BudgetView {

  public BudgetViewImpl() {
    this.presenter = new BudgetPresenter(this);
  }
}
