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
public class CreateCategory extends Operation {
  private final MenuItem[] typeOptions = new MenuItem[] {
      new MenuItem(Transaction.TransactionType.INCOME.name()),
      new MenuItem(Transaction.TransactionType.EXPENSE.name())
  };

  public CreateCategory(MainView view) {
    super(view, "Creating Category", "Choose the category type:");
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

    Category category = new Category(type, name, description);

    view.appendWithNewline(
        UIFormatter.successStyle("Category created."));
    view.appendWithNewline("\n" +
        UIFormatter.highlightStyle("Category:"));
    view.appendWithoutNewline(CategoryFormatter.categoryDetailed(category));

    Database.db().saveCategory(category);
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

  private String inputName() {
    view.appendWithoutNewline(
        UIFormatter.subtitleStyle("Enter a unique category name:"));
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter name", SimpleInput.TEXT));

    NameValidator categoryValidator = new NameValidator();
    String name = "";
    while (!categoryValidator.isValid()) {
      name = SimpleInput.readString();
      categoryValidator.validateName(name);

      if (!categoryValidator.isValid()) {
        name = "";
        view.appendWithNewline("\n" +
            UIFormatter.errorStyle(categoryValidator.getMessages().trim()));
      }
    }

    return name;
  }

  private String inputDescription() {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter description", SimpleInput.TEXT));
    return SimpleInput.readString();
  }
}
