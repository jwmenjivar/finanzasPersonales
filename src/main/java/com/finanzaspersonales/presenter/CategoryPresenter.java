package com.finanzaspersonales.presenter;

import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.operations.*;
import com.finanzaspersonales.presenter.operations.category.CreateCategory;
import com.finanzaspersonales.presenter.operations.category.DeleteCategory;
import com.finanzaspersonales.presenter.operations.category.ShowCategories;
import com.finanzaspersonales.presenter.operations.category.UpdateCategory;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.CategoryView;
import com.finanzaspersonales.view.MainView;

public class CategoryPresenter extends Presenter {
  private final CategoryView categoryView;
  private final CreateCategory createCategory;
  private final ShowCategories showCategories;
  private final DeleteCategory deleteCategory;
  private final UpdateCategory updateCategory;

  public CategoryPresenter(CategoryView categoryView) {
    this.categoryView = categoryView;
    createCategory = new CreateCategory(this.categoryView);
    showCategories = new ShowCategories(this.categoryView);
    deleteCategory = new DeleteCategory(this.categoryView);
    updateCategory = new UpdateCategory(this.categoryView);
  }

  @Override
  public void loadView() {
    String toDisplay = "";

    // HEADER
    toDisplay += UIFormatter.headerStyle("Categories");
    toDisplay = UIFormatter.addNewLine(toDisplay);

    // MENU
    this.menuItems = new MenuItem[]{
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
    toDisplay += UIFormatter.titleStyle("Categories menu");
    toDisplay +=
        UIFormatter.subtitleStyle(
            "Write the number or name of the menu option to navigate to that screen.");
    toDisplay += UIFormatter.menuStyle(menuItems);

    this.categoryView.displayContent(toDisplay);
  }

  @Override
  public Action chooseOperation() {
    String menuOption = MenuInput.handleMenu(
        this.menuItems, this.categoryView);
    Action action = new Action();

    switch (menuOption) {
      case Operation.CREATE -> {
        createCategory.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case Operation.SHOW -> {
        showCategories.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case Operation.DELETE -> {
        deleteCategory.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case Operation.UPDATE -> {
        updateCategory.operate();

        action.setActionType(Action.ActionType.RELOAD);
        return action;
      }
      case "Back" -> {
        action.setActionType(Action.ActionType.NAVIGATION);
        action.setNextView(MainView.getMainView());
        return action;
      }
      default -> {
        return action;
      }
    }
  }
}
