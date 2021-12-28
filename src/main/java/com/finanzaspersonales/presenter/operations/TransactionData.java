package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.*;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.MainView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Represents a transaction operation that manipulates data.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
abstract class TransactionData extends Operation {
  protected Transaction transaction;
  protected final String success;

  TransactionData(MainView view, String title, String subtitle, String success) {
    super(view, title, subtitle);
    this.success = success;
  }

  /**
   * Executes steps to choose a transaction category.
   * Performs a DB get category operation.
   */
  protected void chooseCategory() {
    String prompts = "";
    Category[] categories = Database.db().getCategoriesByType(transaction.getType());
    List<MenuItem> categoryOptions = new ArrayList<>();
    for (Category c : categories) {
      categoryOptions.add(new MenuItem(c.getName()));
    }
    MenuItem[] menuItems = categoryOptions.toArray(new MenuItem[0]);
    prompts += UIFormatter.subtitleStyle("Choose the category type: ");
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = processMenu(menuItems);
    Optional<Category> optionalCategory = Arrays.stream(categories).filter(category1 ->
        category1.getName().equalsIgnoreCase(input)).findFirst();
    optionalCategory.ifPresent(transaction::setCategory);
  }

  /**
   * Executes steps to ask for a transaction amount.
   * Validates the amount against an AmountValidator.
   */
  protected void assignAmount() {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter amount", SimpleInput.NUMBER));

    AmountValidator amountValidator = new AmountValidator();
    double total = 0;
    while(!amountValidator.isValid()) {
      try {
        total = SimpleInput.readDouble();

        if(!amountValidator.validateAmount(total)) {
          total = 0;
          view.appendWithNewline("\n" +
              UIFormatter.errorStyle(amountValidator.getMessages().trim()));
        }
      } catch (Exception e) {
        view.appendWithNewline("\n" +
            UIFormatter.errorStyle(e.getMessage()));
      }
    }
    transaction.setAmount(total);
  }

  /**
   * Executes steps to ask for a transaction description.
   */
  protected void assignDescription() {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter description", SimpleInput.TEXT));
    transaction.setDescription(SimpleInput.readString());
  }

  /**
   * Executes steps to ask for a transaction date.
   * Validates the date against an DateValidator.
   */
  protected void assignDate() {
    String prompts = "";
    prompts += UIFormatter.subtitleStyle("Choose the date: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Today"), new MenuItem("Other day")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = processMenu(menuItems);

    if (input.equals("Today")) {
      transaction.setDate(LocalDate.now());
    } else {
      view.appendWithoutNewline(
          UIFormatter.subtitleStyle("Input the date:"));
      String date = readDate();
      transaction.setDate(LocalDate.parse(date));
    }
  }

  /**
   * Shows the transaction after applying the operation.
   */
  protected void showResult() {
    view.appendWithNewline(
        UIFormatter.successStyle(success));
    view.appendWithNewline("\n" +
        UIFormatter.highlightStyle("Transaction:"));
    view.appendWithoutNewline(TransactionFormatter.transactionDetailed(transaction));
  }

  /**
   * Reads a date with the DateTimeFormatter.ISO_LOCAL_DATE format.
   */
  private String readDate() {
    String date = "";

    DateValidator dateValidator = new DateValidator();
    while(!dateValidator.isValid()) {
      try {
        view.appendWithoutNewline(
            UIFormatter.promptStyle("Enter the date", SimpleInput.DATE));
        date = SimpleInput.readDate();

        if (!dateValidator.validateDate(date)) {
          date = "";
          view.appendWithNewline("\n" +
              UIFormatter.errorStyle(dateValidator.getMessages().trim()));
        }
      } catch (Exception e) {
        view.appendWithNewline("\n" +
            UIFormatter.errorStyle(e.getMessage()));
      }
    }

    return date;
  }
}
