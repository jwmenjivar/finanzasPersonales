package com.finanzaspersonales.presenter.operations.transaction;

import com.finanzaspersonales.model.*;
import com.finanzaspersonales.presenter.operations.Operation;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.TransactionFormatter;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.MainView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a transaction operation that manipulates data.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
abstract class TransactionData extends Operation {
  protected final String success;

  TransactionData(MainView view, String title, String subtitle, String success) {
    super(view, title, subtitle);
    this.success = success;
  }

  /**
   * Executes steps to choose a transaction category.
   * Performs a DB get category operation.
   */
  protected Category inputCategory(Transaction.TransactionType type) {
    Category[] categories = Categories.getByType(type);

    List<MenuItem> categoryOptions = new ArrayList<>();
    for (Category c : categories) {
      categoryOptions.add(new MenuItem(c.getName(), c.getDescription()));
    }
    MenuItem[] menuItems = categoryOptions.toArray(new MenuItem[0]);
    view.appendWithoutNewline(UIFormatter.subtitleStyle("Choose the category type: "));
    String input = processMenu(menuItems);

    return Categories.getByName(input);
  }

  /**
   * Executes steps to ask for a transaction amount.
   * Validates the amount against an AmountValidator.
   */
  protected double inputAmount() {
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

    return total;
  }

  /**
   * Executes steps to ask for a transaction description.
   */
  protected String inputDescription() {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter description", SimpleInput.TEXT));
    return SimpleInput.readString();
  }

  /**
   * Executes steps to ask for a transaction date.
   * Validates the date against an DateValidator.
   */
  protected LocalDate inputDate() {
    String prompts = "";
    prompts += UIFormatter.subtitleStyle("Choose the date: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Today"), new MenuItem("Other day")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = processMenu(menuItems);

    if (input.equals("Today")) {
      return LocalDate.now();
    } else {
      view.appendWithoutNewline(
          UIFormatter.subtitleStyle("Input the date:"));
      String date = readDate();
      return LocalDate.parse(date);
    }
  }

  /**
   * Shows the transaction after applying the operation.
   */
  protected void showResult(Transaction transaction) {
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
