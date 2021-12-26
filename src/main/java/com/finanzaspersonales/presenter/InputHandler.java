package com.finanzaspersonales.presenter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Utility class that handles all the user input.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class InputHandler {
  private static final Scanner scanner = new Scanner(System.in);
  private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

  private InputHandler() { }

  /**
   * Given a list of menu items, asks the user for their input and returns
   * the chosen option.
   * Validates that the input is either:
   * - a number between 1 and the total number of menu items
   * - the name of the option
   * @param options MenuItem list
   * @return A string with the name of the chosen option
   * @throws IOException Thrown if the input is invalid
   */
  @NotNull
  @Contract(pure = true)
  public static String readMenuOption(@NotNull List<MenuItem> options) throws IOException {
    String in = scanner.nextLine();

    if (in != null) {
      if (isNumeric(in)) {
        int number = Integer.parseInt(in);
        // number is between 1 and options.size() inclusive
        boolean withinRange = 0 < number && number <= options.size();
        if (withinRange) {
          return options.get(number - 1).getItem();
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
    }

    // VERIFY: not sure if this is the best exception to use
    throw new IOException("Can not recognize the option. Try again.");
  }

  /**
   * Verifies if a string is numeric.
   * @param strNum String to validate
   * @return boolean
   */
  private static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    return pattern.matcher(strNum).matches();
  }
}
