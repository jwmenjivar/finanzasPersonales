package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.*;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CreateTransaction {

  private CreateTransaction() { }

  public static void create(@NotNull MainView view) {
    Transaction t = newTransaction(view);
    chooseCategory(view, t);
    assignDate(view, t);

    view.showPrompt(
        UIFormatter.subtitleStyle("Enter transaction amount and description:"));
    assignAmount(view, t);
    assignDescription(view, t);

    view.appendContent(
        UIFormatter.successStyle("Transaction created."));
    view.appendContent("\n" +
        UIFormatter.highlightStyle("New transaction:"));
    view.appendContent(TransactionFormatter.transactionDetailed(t));

    Database.db().saveTransaction(t);
    view.showPrompt(
        UIFormatter.confirmationPromptStyle("[Press ENTER to continue]"));
    InputReader.readString();
  }

  private static Transaction newTransaction(@NotNull MainView view) {
    String prompts = "\n";
    prompts += UIFormatter.titleStyle("Creating a new transaction");
    prompts += UIFormatter.subtitleStyle("Choose the transaction type: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Income"), new MenuItem("Expense")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendContent(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuHandler.handleMenuOption(menuItems, view);
    }

    if (input.equals("Income")) {
      return Transaction.makeIncomeTransaction();
    } else {
      return Transaction.makeExpenseTransaction();
    }
  }

  private static void chooseCategory(MainView view, @NotNull Transaction t) {
    String prompts = "";
    Category[] categories = Database.db().getCategoriesByType(t.getType());
    List<MenuItem> categoryOptions = new ArrayList<>();
    for (Category c : categories) {
      categoryOptions.add(new MenuItem(c.getName()));
    }
    MenuItem[] menuItems = categoryOptions.toArray(new MenuItem[0]);
    prompts += UIFormatter.subtitleStyle("Choose the category type: ");
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendContent(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuHandler.handleMenuOption(menuItems, view);
    }
    String finalInput = input;
    Optional<Category> optionalCategory = Arrays.stream(categories).filter(category1 ->
        category1.getName().equalsIgnoreCase(finalInput)).findFirst();
    optionalCategory.ifPresent(t::setCategory);
  }

  private static void assignAmount(@NotNull MainView view, Transaction t) {
    view.showPrompt(
        UIFormatter.promptStyle("Enter total", InputReader.NUMBER));

    AmountValidator amountValidator = new AmountValidator();
    double total = 0;
    while(!amountValidator.isValid()) {
      try {
        total = InputReader.readDouble();

        if(!amountValidator.validateAmount(total)) {
          total = 0;
          view.appendContent("\n" +
              UIFormatter.errorStyle(amountValidator.getMessages().trim()));
        }
      } catch (Exception e) {
        view.appendContent("\n" +
            UIFormatter.errorStyle(e.getMessage()));
      }
    }
    t.setAmount(total);
  }

  private static void assignDescription(@NotNull MainView view, @NotNull Transaction t) {
    view.showPrompt(
        UIFormatter.promptStyle("Enter description", InputReader.TEXT));
    t.setDescription(InputReader.readString());
  }

  private static void assignDate(@NotNull MainView view, Transaction t) {
    String prompts = "";
    prompts += UIFormatter.subtitleStyle("Choose the date: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Today"), new MenuItem("Other day")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendContent(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuHandler.handleMenuOption(menuItems, view);
    }

    if (input.equals("Today")) {
      t.setDate(LocalDate.now());
    } else {
      view.showPrompt(
          UIFormatter.subtitleStyle("Input the date:"));
      String date = readDate(view);
      t.setDate(LocalDate.parse(date));
    }
  }

  private static String readDate(MainView view) {
    String date = "";

    DateValidator dateValidator = new DateValidator();
    while(!dateValidator.isValid()) {
      try {
        view.showPrompt(
            UIFormatter.promptStyle("Enter the date", InputReader.DATE));
        date = InputReader.readDate();

        if (!dateValidator.validateDate(date)) {
          date = "";
          view.appendContent("\n" +
              UIFormatter.errorStyle(dateValidator.getMessages().trim()));
        }
      } catch (Exception e) {
        view.appendContent("\n" +
            UIFormatter.errorStyle(e.getMessage()));
      }
    }

    return date;
  }
}
