package com.finanzaspersonales.presenter.operations.category;

import com.finanzaspersonales.model.db.Categories;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.operations.ModelOperation;
import com.finanzaspersonales.presenter.ui.CategoryFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

/**
 * Operation to show all the existing categories.
 * Transactions can be displayed in a summarized or detailed style.
 *
 * To use, instantiate and provide the view where it will print all
 * the prompts.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class ShowCategories extends ModelOperation {
  private final MenuItem[] displayOptions =
      new MenuItem[] { new MenuItem("Summarized"), new MenuItem("Detailed") };

  public ShowCategories(View view) {
    super(view, "Showing categories", "View format: ");
  }

  /**
   * Operation that shows all existing categories in summarized or detailed style.
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
    view.append("\n" +UIFormatter.titleStyle("Summarized categories:"));
    view.append(
        CategoryFormatter.categoryTable(Categories.getAll()));
  }

  private void showDetailed() {
    view.append("\n" +UIFormatter.titleStyle("Detailed categories:"));
    view.append(
        CategoryFormatter.categoriesDetailed(Categories.getAll()));
  }
}