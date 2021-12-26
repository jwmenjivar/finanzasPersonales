package com.finanzaspersonales.view;

import com.finanzaspersonales.presenter.CategoryPresenter;

class CategoryViewImpl extends MainViewImpl implements CategoryView {

  public CategoryViewImpl() {
    this.presenter = new CategoryPresenter(this);
  }
}
