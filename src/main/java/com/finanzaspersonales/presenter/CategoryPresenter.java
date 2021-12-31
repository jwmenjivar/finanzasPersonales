package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.operations.*;
import com.finanzaspersonales.presenter.operations.category.CreateCategory;
import com.finanzaspersonales.presenter.operations.category.DeleteCategory;
import com.finanzaspersonales.presenter.operations.category.ShowCategories;
import com.finanzaspersonales.presenter.operations.category.UpdateCategory;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

public class CategoryPresenter extends Presenter {
  private final CreateCategory createCategory;
  private final ShowCategories showCategories;
  private final DeleteCategory deleteCategory;
  private final UpdateCategory updateCategory;

  public CategoryPresenter(View view) {
    super(view);
    createCategory = new CreateCategory(this.view);
    showCategories = new ShowCategories(this.view);
    deleteCategory = new DeleteCategory(this.view);
    updateCategory = new UpdateCategory(this.view);

    menuItems = new MenuItem[]{
        new MenuItem(
            Operation.CREATE,
            "Create new category."),
        new MenuItem(
            Operation.SHOW,
            "Show recorded categories."),
        new MenuItem(
            Operation.DELETE,
            "Delete recorded categories."),
        new MenuItem(
            Operation.UPDATE,
            "Update recorded category."),
        new MenuItem(
            "Back",
            "Back to the main menu.")};
  }

  @Override
  protected Action chooseOperation(@NotNull String operation) {
    switch (operation) {
      case Operation.CREATE -> {
        createCategory.operate();
        return Action.RELOAD;
      }
      case Operation.SHOW -> {
        showCategories.operate();
        return Action.RELOAD;
      }
      case Operation.DELETE -> {
        deleteCategory.operate();
        return Action.RELOAD;
      }
      case Operation.UPDATE -> {
        updateCategory.operate();
        return Action.RELOAD;
      }
      case "Back" -> {
        return Action.MENU;
      }
      default -> {
        return Action.NONE;
      }
    }
  }

  @Override
  protected void loadView() {
    view.initialize(
        "Categories",
        "",
        "Categories menu",
        "Write the number or name of the menu option to navigate to that screen.",
        UIFormatter.menuStyle(menuItems));
  }
}
