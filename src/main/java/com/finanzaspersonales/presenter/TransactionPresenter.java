package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

public class TransactionPresenter extends Presenter {

  public TransactionPresenter(View view, String name) {
    super(view, name);
  }

  @Override
  protected void loadView() {
    view.initialize(
        "Transactions",
        "",
        "Transactions menu",
        "Write the number or name of the menu option to navigate to that screen.",
        UIFormatter.menuStyle(menuItems.toArray(MenuItem[]::new)));
  }
}
