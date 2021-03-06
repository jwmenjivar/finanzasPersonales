package com.finanzaspersonales.presenter.operations.category;

import com.finanzaspersonales.model.data.Category;
import com.finanzaspersonales.model.data.validators.CategoryNameValidator;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.operations.ModelOperation;
import com.finanzaspersonales.presenter.ui.CategoryFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

public class CategoryData extends ModelOperation {
  protected final String success;

  protected CategoryData(View view, String title, String subtitle, String success) {
    super(view, title, subtitle);
    this.success = success;
  }

  protected String inputName() {
    view.append(
        UIFormatter.subtitleStyle("Enter a unique category name:"));

    CategoryNameValidator categoryValidator = new CategoryNameValidator();
    String name = "";
    while (!categoryValidator.isValid()) {
      view.prompt("Enter name", SimpleInput.TEXT);
      name = SimpleInput.readString();
      categoryValidator.validate(name);

      if (!categoryValidator.isValid()) {
        name = "";
        view.error(categoryValidator.getMessages().trim());
      }
    }

    return name;
  }

  protected void showResult(Category category) {
    view.append(success);
    view.append("\n" +
        UIFormatter.highlightStyle("Category:"));
    view.append(CategoryFormatter.categoryDetailed(category));
  }
}
