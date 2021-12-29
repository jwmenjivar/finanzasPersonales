package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Category;
import com.finanzaspersonales.model.NameValidator;
import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.CategoryFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

/**
 * Operation to create a new category.
 * It asks for all the necessary data, validating it as it goes.
 *
 * To use, instantiate and provide the view where it will print all
 * the prompts.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class CreateCategory extends CategoryData {
  private final MenuItem[] typeOptions = new MenuItem[] {
      new MenuItem(Transaction.TransactionType.INCOME.name()),
      new MenuItem(Transaction.TransactionType.EXPENSE.name())
  };

  public CreateCategory(MainView view) {
    super(view,
        "Creating Category",
        "Choose the category type:",
        "Category created.");
  }

  /**
   * Operation that creates a new transaction following these steps:
   * 1. Ask for the transaction type
   * 2. Ask for the category name
   * 3. Ask for the description
   * 4. Create and save the category
   *
   * It performs a DB save operation.
   */
  public void create() {
    startOperation();

    Transaction.TransactionType type = inputTransactionType();
    String name = inputName();
    String description = inputDescription();

    this.category = new Category(type, name, description);

    showResult();

    Database.db().saveCategory(this.category);
    endOperation();
  }

  private Transaction.TransactionType inputTransactionType() {
    String input = processMenu(typeOptions);

    if (input.equals(Transaction.TransactionType.INCOME.name())) {
      return Transaction.TransactionType.INCOME;
    } else {
      return Transaction.TransactionType.EXPENSE;
    }
  }
}
