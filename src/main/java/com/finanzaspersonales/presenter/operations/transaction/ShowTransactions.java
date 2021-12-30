package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.Transactions;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.operations.Operation;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

/**
 * Operation to show all the existing transactions.
 * Transactions can be displayed in a summarized or detailed style.
 *
 * To use, instantiate and provide the view where it will print all
 * the prompts.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class ShowTransactions extends Operation {
  private final MenuItem[] displayOptions =
      new MenuItem[] { new MenuItem("Summarized"), new MenuItem("Detailed") };

  public ShowTransactions(View view) {
    super(view, "Showing transactions", "View format: ");
  }

  /**
   * Operation that shows all existing transactions in summarized or detailed style.
   * 1. Asks which style to use.
   * 2. Prints all the transactions.
   *
   * It performs a DB get operation.
   */
  @Override
  protected void operation() {
    String input = MenuInput.processMenu(displayOptions, view);

    if (input.equals("Summarized")) {
      showSummarized();
    } else {
      showDetailed();
    }
  }

  private void showSummarized() {
    view.appendWithoutNewline(UIFormatter.titleStyle("Summarized transactions:"));
    view.appendWithNewline(
        TransactionFormatter.transactionsTable(Transactions.getAll()));
  }

  private void showDetailed() {
    view.appendWithoutNewline(UIFormatter.titleStyle("Detailed transactions:"));
    view.appendWithNewline(
        TransactionFormatter.transactionsDetailed(Transactions.getAll()));
  }
}
