package com.finanzaspersonales.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

/**
 * Validates a String with a date against the DateTimeFormatter.ISO_LOCAL_DATE.
 * Stores the result of the validation and any error messages if the date is not valid.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class DateValidator {
  private String messages = "";
  private boolean isValid = false;

  /**
   * Validates a string with a date following the DateTimeFormatter.ISO_LOCAL_DATE.
   * @param date String date [yyyy-mm-dd]
   */
  public boolean validateDate(@NotNull String date) {
    String[] parts = date.split("-");
    int year = Integer.parseInt(parts[0]);
    int month = Integer.parseInt(parts[1]);
    int day = Integer.parseInt(parts[2]);

    boolean yearValid = this.validateYear(year);
    boolean monthValid = this.validateMonth(month);
    boolean dayValid = this.validateDay(year, month, day);

    this.isValid = yearValid && monthValid && dayValid;

    return this.isValid;
  }

  /**
   * Validates that the day is within the appropriate boundaries depending on the month.
   * Takes the month and year into account to validate the maximum number.
   */
  private boolean validateDay(int year, int month, int day) {
    if (day == 0) {
      this.messages += "The day must be greater than zero.\n";
    } else if (day > 31) {
      this.messages += "The value can not be more than 31.\n";
    } else if (day > 30 && (month==4 || month==6 || month==9 || month==11)) {
      this.messages += "The chosen month only has 30 days.\n";
    } else if (day > 29 && month == 2 && isLeapYear(year)) {
      this.messages += "February only has 29 days in leap year " + year + ".\n";
    } else if (day > 28 && month == 2 && !isLeapYear(year)) {
      this.messages += "February only has 28 days in non-leap year " + year + ".\n";
    } else {
      return true;
    }

    return false;
  }

  /**
   * Validates that the month is within 1 and 12.
   */
  private boolean validateMonth(int month) {
    if (month == 0) {
      this.messages += "The month must be greater than zero.\n";
    } else if (month > 12) {
      this.messages += "The month must be between 1 and 12.\n";
    } else {
      return true;
    }

    return false;
  }

  /**
   * Validates that the year is greater than 2000 and less than the current year.
   */
  private boolean validateYear(int year) {
    if (year < 2000) {
      this.messages += "The minimum year is 2000.\n";
    } else if (year > LocalDate.now().getYear()){
      this.messages += "The maximum year is the current year.\n";
    } else {
      return true;
    }

    return false;
  }

  /**
   * Verifies if a year is leap.
   */
  private boolean isLeapYear(int year) {
    // year to be checked
    boolean leap = false;

    // if the year is divided by 4
    if (year % 4 == 0) {
      // if the year is century
      if (year % 100 == 0) {
        // if year is divided by 400
        // then it is a leap year
        if (year % 400 == 0) {
          leap = true;
        } else {
          leap = false;
        }
      } else {
        // if the year is not century
        leap = true;
      }
    } else {
      leap = false;
    }

    return leap;
  }
}
