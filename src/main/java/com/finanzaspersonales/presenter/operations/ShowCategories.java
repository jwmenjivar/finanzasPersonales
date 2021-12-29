package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Category;
import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.presenter.ui.CategoryFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

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
public class ShowCategories extends Operation {
  private final MenuItem[] displayOptions =
      new MenuItem[] { new MenuItem("Summarized"), new MenuItem("Detailed") };

  public ShowCategories(MainView view) {
    super(view, "Showing categories", "View format: ");
  }

  /**
   * Operation that shows all existing categories in summarized or detailed style.
   * 1. Asks which style to use.
   * 2. Prints all the transactions.
   *
   * It performs a DB get operation.
   */
  public void showAll() {
    startOperation();
    String input = processMenu(displayOptions);

    if (input.equals("Summarized")) {
      showSummarized();
    } else {
      showDetailed();
    }

    endOperation();
  }

  private void showSummarized() {
    view.appendWithoutNewline(UIFormatter.titleStyle("Summarized categories:"));
    view.appendWithNewline(
        CategoryFormatter.categoryTable(Database.db().getAllCategories()));
  }

  private void showDetailed() {
    view.appendWithoutNewline(UIFormatter.titleStyle("Detailed categories:"));

    Category[] categories = Database.db().getAllCategories();
    view.appendWithNewline(CategoryFormatter.categoriesDetailed(categories));
  }
}