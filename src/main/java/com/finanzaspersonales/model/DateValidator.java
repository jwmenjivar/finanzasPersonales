package com.finanzaspersonales.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Validates a String with a date against the DateTimeFormatter.ISO_LOCAL_DATE.
 * Stores the result of the validation and any error messages if the date is not valid.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public final class DateValidator extends Validator {

  /**
   * Validates a string with a date following the DateTimeFormatter.ISO_LOCAL_DATE.
   * @param date String date [yyyy-mm-dd]
   */
  public boolean validate(@NotNull String date) {
    this.messages = "";
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
      this.messages += "The day must be greater than zero.";
    } else if (!YearMonth.of(year, month).isValidDay(day)) {
      this.messages += "Invalid day for the month and year provided.";
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
      this.messages += "The month must be greater than zero.";
    } else if (month > 12) {
      this.messages += "The month must be between 1 and 12.";
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
      this.messages += "The minimum year is 2000.";
    } else if (year > LocalDate.now().getYear()){
      this.messages += "The maximum year is the current year.";
    } else {
      return true;
    }

    return false;
  }
}
