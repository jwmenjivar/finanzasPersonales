package com.finanzaspersonales.presenter;

import com.finanzaspersonales.model.*;
import com.finanzaspersonales.view.MainView;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TransactionInput {

  TransactionInput() { }

  public static void chooseCategory(MainView view, @NotNull Transaction t) {
    String prompts = "";
    Category[] categories = Database.db().getCategoriesByType(t.getType());
    List<MenuItem> categoryOptions = new ArrayList<>();
    for (Category c : categories) {
      categoryOptions.add(new MenuItem(c.getName()));
    }
    MenuItem[] menuItems = categoryOptions.toArray(new MenuItem[0]);
    prompts += UIFormatter.subtitleStyle("Choose the category type: ");
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuInput.handleMenu(menuItems, view);
    }
    String finalInput = input;
    Optional<Category> optionalCategory = Arrays.stream(categories).filter(category1 ->
        category1.getName().equalsIgnoreCase(finalInput)).findFirst();
    optionalCategory.ifPresent(t::setCategory);
  }

  public static void assignAmount(@NotNull MainView view, Transaction t) {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter amount", InputReader.NUMBER));

    AmountValidator amountValidator = new AmountValidator();
    double total = 0;
    while(!amountValidator.isValid()) {
      try {
        total = InputReader.readDouble();

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
    t.setAmount(total);
  }

  public static void assignDescription(@NotNull MainView view, @NotNull Transaction t) {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter description", InputReader.TEXT));
    t.setDescription(InputReader.readString());
  }

  public static void assignDate(@NotNull MainView view, Transaction t) {
    String prompts = "";
    prompts += UIFormatter.subtitleStyle("Choose the date: ");
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Today"), new MenuItem("Other day")};
    prompts += UIFormatter.menuStyle(menuItems);
    view.appendWithNewline(prompts);

    String input = "";
    while (input.isEmpty()) {
      input = MenuInput.handleMenu(menuItems, view);
    }

    if (input.equals("Today")) {
      t.setDate(LocalDate.now());
    } else {
      view.appendWithoutNewline(
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
        view.appendWithoutNewline(
            UIFormatter.promptStyle("Enter the date", InputReader.DATE));
        date = InputReader.readDate();

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
