package com.finanzaspersonales.presenter.operations.category;

import com.finanzaspersonales.model.Categories;
import com.finanzaspersonales.model.Category;
import com.finanzaspersonales.presenter.input.DataInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.CategoryFormatter;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

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

  public UpdateCategory(View view) {
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
  @Override
  protected void operation() {
    view.prompt("Enter name", SimpleInput.TEXT);
    String name = SimpleInput.readString();

    if (Categories.exists(name)) {
      Category category = Categories.getByName(name);
      view.append("\n" + CategoryFormatter.categoryDetailed(category));

      view.append(UIFormatter.subtitleStyle("\n" + "Choose what to edit: "));
      MenuItem[] menuItems = new MenuItem[]{
          new MenuItem(CategoryFormatter.NAME_H),
          new MenuItem(CategoryFormatter.DESCRIPTION_H),
          new MenuItem("Back")
      };

      String input = MenuInput.processMenu(menuItems, view);

      if (!input.equals("Back")) {
        switch (input) {
          case CategoryFormatter.NAME_H -> category.setName(inputName());
          case CategoryFormatter.DESCRIPTION_H ->
              category.setDescription(DataInput.inputDescription(view));
          default -> { /* go back */ }
        }

        Categories.update(category);
        showResult(category);
      }

    } else {
      view.error("Invalid or non existent ID.");
    }
  }
}
