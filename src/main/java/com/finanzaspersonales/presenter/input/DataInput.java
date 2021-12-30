package com.finanzaspersonales.presenter.input;

import com.finanzaspersonales.model.AmountValidator;
import com.finanzaspersonales.model.DateValidator;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class DataInput extends SimpleInput {

  /**
   * Executes steps to ask for a transaction amount.
   * Validates the amount against an AmountValidator.
   */
  public static double inputAmount(@NotNull View view) {
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
  @NotNull
  public static String inputDescription(@NotNull View view) {
    view.appendWithoutNewline(
        UIFormatter.promptStyle("Enter description", SimpleInput.TEXT));
    return SimpleInput.readString();
  }

  /**
   * Executes steps to ask for a transaction date.
   * Validates the date against an DateValidator.
   */
  public static LocalDate inputDate(@NotNull View view) {
    view.appendWithoutNewline(UIFormatter.subtitleStyle("Choose the date: "));
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Today"), new MenuItem("Other day")};

    String input = MenuInput.processMenu(menuItems, view);

    if (input.equals("Today")) {
      return LocalDate.now();
    } else {
      view.appendWithoutNewline(
          UIFormatter.subtitleStyle("Input the date:"));
      String date = readDate(view);
      return LocalDate.parse(date);
    }
  }

  /**
   * Reads a date with the DateTimeFormatter.ISO_LOCAL_DATE format.
   */
  private static String readDate(View view) {
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
