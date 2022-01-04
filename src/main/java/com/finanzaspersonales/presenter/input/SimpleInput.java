package com.finanzaspersonales.presenter.input;

import com.finanzaspersonales.presenter.ui.MenuItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Utility class that handles all the user input.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class SimpleInput {
  public static final String OPTIONS = "[integer/name]";
  public static final String NUMBER = "[number]";
  public static final String DATE = "[yyyy-mm-dd]";
  public static final String DATE_YEAR = "[yyyy]";
  public static final String TEXT = "[text]";
  public static final String YES_NO = "Y/N";
  private static final Scanner scanner = new Scanner(System.in);
  private static final Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
  private static final Pattern datePattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{2}");
  private static final Pattern yearPattern = Pattern.compile("\\d{4}");
  // from https://www.baeldung.com/java-email-validation-regex
  private static final Pattern emailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]"+
      "+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

  protected SimpleInput() { }

  /**
   * Given a list of menu items, asks the user for their input and returns
   * the chosen option.
   * Validates that the input is either:
   * - a number between 1 and the total number of menu items
   * - the name of the option
   * @param options MenuItem list
   * @return A string with the name of the chosen option
   */
  @NotNull
  @Contract(pure = true)
  static String readMenuOption(@NotNull MenuItem[] options) throws InputMismatchException {
    String in = readString();

    if (isNumeric(in)) {
      int number = Integer.parseInt(in);
      // number is between 1 and options.size() inclusive
      boolean withinRange = 0 < number && number <= options.length;
      if (withinRange) {
        return options[number - 1].getItem();
      }
    } else {
      for (MenuItem item : options) {
        // ignoring the case, the name has to match exactly
        // MAYBE: match the first letter only for quicker navigation
        if (item.getItem().equalsIgnoreCase(in.toLowerCase())) {
          return item.getItem();
        }
      }
    }

    throw new InputMismatchException("Can not recognize the option.");
  }

  @NotNull
  static String readYesOrNo() throws InputMismatchException {
    String in = readString();

    if (in.equals("Y") || in.equals("y") || in.equals("N") || in.equals("n")) {
      return in;
    }

    throw new InputMismatchException("Input yes (Y/y) or no (N/n).");
  }

  /**
   * Reads a double.
   */
  static double readDouble() throws InputMismatchException {
    String in = readString();

    if (isNumeric(in)) {
      return Double.parseDouble(in);
    }

    throw new InputMismatchException("Invalid number.");
  }

  /**
   * Reads an integer.
   */
  static int readInteger() throws InputMismatchException {
    String in = readString();

    if (isNumeric(in)) {
      return Integer.parseInt(in);
    }

    throw new InputMismatchException("Invalid integer.");
  }

  /**
   * Reads a date that follows the DateTimeFormatter.ISO_LOCAL_DATE
   * @return String with DateTimeFormatter.ISO_LOCAL_DATE date
   */
  @NotNull
  static String readDate() throws InputMismatchException {
    String in = readString();

    if (datePattern.matcher(in).matches()) {
      return in;
    }

    throw new InputMismatchException("Invalid date. Valid format [yyyy-mm-dd].");
  }

  /**
   * Reads a year that follows the [yyyy] format
   * @return String with [yyyy]
   */
  static int readYear() throws InputMismatchException {
    String in = readString();

    if (yearPattern.matcher(in).matches()) {
      return Integer.parseInt(in);
    }

    throw new InputMismatchException("Invalid date. Valid format: [yyyy-mm-dd].");
  }

  @NotNull
  static String readEmail() throws InputMismatchException {
    String in = readString();
    if (emailPattern.matcher(in.trim()).matches()) {
      return in;
    }

    throw new InputMismatchException("Invalid email. Valid format: name@domain.");
  }

  /**
   * Reads a string.
   */
  @NotNull
  public static String readString() {
    return scanner.nextLine().trim();
  }

  /**
   * Verifies if a string is numeric.
   * @param strNum String to validate
   */
  private static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    return numericPattern.matcher(strNum).matches();
  }
}
