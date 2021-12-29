package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Category;
import com.finanzaspersonales.model.NameValidator;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.CategoryFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

public class CategoryData extends Operation {
  protected final String success;
  protected Category category;

  protected CategoryData(MainView view, String title, String subtitle, String success) {
    super(view, title, subtitle);
    this.success = success;
  }

  protected String inputName() {
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

  protected String inputDescription() {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter description", SimpleInput.TEXT));
    return SimpleInput.readString();
  }

  protected void showResult() {
    view.appendWithNewline(
        UIFormatter.successStyle(success));
    view.appendWithNewline("\n" +
        UIFormatter.highlightStyle("Category:"));
    view.appendWithoutNewline(CategoryFormatter.categoryDetailed(category));
  }
}
