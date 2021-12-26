package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.TransactionPresenter;

class TransactionViewImpl extends MainViewImpl implements TransactionView {

  public TransactionViewImpl() {
    this.presenter = new TransactionPresenter(this);
  }
}
