package com.finanzaspersonales.presenter.input;

import com.finanzaspersonales.model.TransactionAmountValidator;
import com.finanzaspersonales.model.DateValidator;
import com.finanzaspersonales.presenter.ui.MenuItem;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.InputMismatchException;

public class DataInput extends SimpleInput {

  /**
   * Executes steps to ask for a transaction amount.
   * Validates the amount against an AmountValidator.
   */
  public static double inputAmount(@NotNull View view) {
    TransactionAmountValidator amountValidator = new TransactionAmountValidator();
    double total = 0;
    while(!amountValidator.isValid()) {
      try {
        view.prompt("Enter amount", SimpleInput.NUMBER);
        total = SimpleInput.readDouble();

        if(!amountValidator.validate(total)) {
          total = 0;
          view.error(amountValidator.getMessages());
        }
      } catch (Exception e) {
        view.error(e.getMessage());
      }
    }

    return total;
  }

  /**
   * Executes steps to ask for a transaction description.
   */
  @NotNull
  public static String inputDescription(@NotNull View view) {
    view.prompt("Enter description", SimpleInput.NUMBER);
    return SimpleInput.readString();
  }

  /**
   * Executes steps to ask for a transaction date.
   * Validates the date against an DateValidator.
   */
  public static LocalDate inputDate(@NotNull View view) {
    view.append(UIFormatter.subtitleStyle("Choose the date: "));
    MenuItem[] menuItems = new MenuItem[]{new MenuItem("Today"), new MenuItem("Other day")};

    String input = MenuInput.processMenu(menuItems, view);

    if (input.equals("Today")) {
      return LocalDate.now();
    } else {
      view.append(
          UIFormatter.subtitleStyle("Input the date:"));
      String date = readDate(view);
      return LocalDate.parse(date);
    }
  }

  /**
   * Executes steps to ask for a year with the format [yyyy].
   */
  public static int inputYear(@NotNull View view) {
    view.append(
        UIFormatter.subtitleStyle("Input the year number:"));
    view.prompt("Enter year", SimpleInput.DATE_YEAR);

    int year;
    try {
      year = SimpleInput.readYear();
    } catch (InputMismatchException e) {
      view.error("Invalid year. Setting to current year.");
      year = LocalDate.now().getYear();
    }
    return year;
  }

  /**
   * Reads a date with the DateTimeFormatter.ISO_LOCAL_DATE format.
   */
  private static String readDate(View view) {
    String date = "";

    DateValidator dateValidator = new DateValidator();
    while(!dateValidator.isValid()) {
      try {
        view.prompt("Enter date", SimpleInput.DATE);
        date = SimpleInput.readDate();

          if (!dateValidator.validate(date)) {
          date = "";
          view.error(dateValidator.getMessages());
        }
      } catch (Exception e) {
        view.error(e.getMessage());
      }
    }
    view.append("\n");

    return date;
  }
}
