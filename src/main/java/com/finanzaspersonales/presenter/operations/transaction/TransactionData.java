package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.data.Category;
import com.finanzaspersonales.model.data.Transaction;
import com.finanzaspersonales.model.db.Categories;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.operations.ModelOperation;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a transaction operation that manipulates data.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
abstract class TransactionData extends ModelOperation {
  protected final String success;

  TransactionData(View view, String title, String subtitle, String success) {
    super(view, title, subtitle);
    this.success = success;
  }

  /**
   * Executes steps to choose a transaction category.
   * Performs a DB get category operation.
   */
  protected Category inputCategory(Transaction.TransactionType type) {
    Category[] categories = Categories.getAllByType(type);

    List<MenuItem> categoryOptions = new ArrayList<>();
    for (Category c : categories) {
      categoryOptions.add(new MenuItem(c.getName(), c.getDescription()));
    }
    MenuItem[] menuItems = categoryOptions.toArray(new MenuItem[0]);
    view.append(UIFormatter.subtitleStyle("Choose the category type: "));
    String input = MenuInput.processMenu(menuItems, view);

    return Categories.getByName(input);
  }

  /**
   * Shows the transaction after applying the operation.
   */
  protected void showResult(Transaction transaction) {
    view.success(success);
    view.append("\n" +
        UIFormatter.highlightStyle("Transaction:"));
    view.append(TransactionFormatter.transactionDetailed(transaction));
  }
}
