package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.ReportPresenter;

class ReportViewImpl extends MainViewImpl implements ReportView {

  public ReportViewImpl() {
    this.presenter = new ReportPresenter(this);
  }
}
