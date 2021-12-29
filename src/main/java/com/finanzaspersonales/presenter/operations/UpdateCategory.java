package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Category;
import com.finanzaspersonales.model.Database;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.CategoryFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

/**
 * Operation to update an attribute of an existing category.
 * It asks for which attribute to modify, and then prompts asks
 * for the new value.
 *
 * To use, instantiate and provide the view where it will print all
 * the prompts.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class UpdateCategory extends CategoryData {

  public UpdateCategory(MainView view) {
    super(view,
        "Updating a Category",
        "Enter an existing name: ",
        "Category updated.");
  }

  /**
   * Operation that updates an existing category.
   * 1. Asks for the category name.
   * 2. If it exists, asks for the attribute to edit.
   * 3. Asks for the new value.
   * 4. Updates the category.
   * 5. Ends the operation.
   *
   * It performs a DB update operation.
   */
  public void update() {
    startOperation();

    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter name", SimpleInput.TEXT));

    String name = SimpleInput.readString();
    if (Database.db().categoryExists(name)) {
      this.category = Database.db().getCategoryByName(name);
      view.appendWithNewline("\n" + CategoryFormatter.categoryDetailed(category));

      view.appendWithoutNewline(UIFormatter.subtitleStyle("Choose what to edit: "));
      MenuItem[] menuItems = new MenuItem[]{
          new MenuItem(CategoryFormatter.NAME_H),
          new MenuItem(CategoryFormatter.DESCRIPTION_H),
          new MenuItem("Back")
      };

      String input = processMenu(menuItems);

      switch (input) {
        case CategoryFormatter.NAME_H -> category.setName(inputName());
        case CategoryFormatter.DESCRIPTION_H -> category.setDescription(inputDescription());
        default -> { /* go back */ }
      }

      if (!input.equals("Back")) {
        Database.db().updateCategory(category);
        showResult();
      }
    } else {
      view.appendWithNewline(UIFormatter.errorStyle("Invalid or non existent ID."));
    }

    endOperation();
  }
}
