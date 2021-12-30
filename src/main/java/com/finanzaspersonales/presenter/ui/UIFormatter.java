package com.finanzaspersonales.presenter.ui;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.io.Colors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class that uses 'jansi' to format all the strings to UI elements.
 * All methods receive a string and returns a formatted string.
 */
public class UIFormatter {
  // width of the screen
  private static final int MAX_WIDTH = 80;
  private static final String PROMPT_END = "$>";

  private UIFormatter() { }

  /**
   * Centers the text in the view based on the max WIDTH.
   * @param text String with no ANSI sequence
   * @return Formatted string with preceding and leading spaces
   */
  @NotNull
  public static String center(@NotNull String text) {
    return center(text, MAX_WIDTH);
  }

  /**
   * Centers the string in the view based on the provided width.
   * @param text String with no ANSI sequence
   * @param width Width must be greater than the string length
   * @return Formatted string with preceding and leading spaces
   */
  @NotNull
  public static String center(@NotNull String text, int width) {
    int spacing = (width - text.length()) / 2;
    if (spacing > 0) {
      return " ".repeat(spacing) + text + " ".repeat(spacing);
    }

    return text;
  }

  /**
   * Aligns a text to the left adding empty spaces equal to the offset.
   * @param text String with no ANSI sequence
   * @param space Must be less than the MAX_WIDTH
   * @return Formatted string with preceding spaces
   */
  @NotNull
  public static String textAlignLeft(@NotNull String text, int space) {
    space -= text.length();
    if (space > 0) {
      text += " ".repeat(space);
    }

    return text;
  }

  /**
   * Aligns a text to the right adding empty spaces equal to the offset.
   * @param text String with no ANSI sequence
   * @param space Must be less than the MAX_WIDTH
   * @return Formatted string with leading spaces
   */
  @NotNull
  public static String textAlignRight(@NotNull String text, int space) {
    space -= text.length();

    if (space > 0) {
      text = " ".repeat(space) + text;
    }

    return wrapText(text);
  }

  /**
   * Wraps text to the MAX_WIDTH.
   * @param text String with no ANSI sequence
   * @return String with multiple lines
   */
  @NotNull
  public static String wrapText(@NotNull String text) {
    return wrapText(text, MAX_WIDTH);
  }

  @NotNull
  private static String wrapText(@NotNull String text, int space) {
    if (text.length() > space) {
      StringBuilder temp = new StringBuilder();
      int start = 0;
      int end = space;

      while ((text.length() - space) > 0) {
        temp.append(text, start, end).append("\n");
        text = text.substring(end);
      }

      text = temp + text;
    }

    return text;
  }

  /**
   * Formats a list of menu items into a string ui element.
   * @return ANSI String with numbered list of menu items
   */
  @NotNull
  public static String menuStyle(@NotNull MenuItem[] menuItems) {
    int count = 1;
    StringBuilder menu = new StringBuilder();

    int longestLength = 0;
    for (MenuItem s : menuItems) {
      if (longestLength < s.getItem().length()) {
        longestLength = s.getItem().length();
      }
    }

    // add each item with its description beside it
    for (MenuItem s : menuItems) {
      // every item name should use the same amount of space to make the
      // descriptions align
      String offsetSpace = " ".repeat(longestLength - s.getItem().length());
      String element = String.format("%d. %s%s", count, s.getItem(), offsetSpace);
      menu.append(element);

      if (s.getDescription() != null) {
        String details = "\t"
            + Ansi.ansi().fg(Color.WHITE)
            .a(Ansi.Attribute.ITALIC)
            .a(s.getDescription()).reset().toString();
        menu.append(details);
      }
      menu.append("\n");

      count++;
    }

    return menu.toString().trim();
  }

  /**
   * Formats a text with the header style.
   * Header style: Magenta background color, centered and uppercase.
   * @param header String with no ANSI sequence
   * @return ANSI String with the formatted header
   */
  @NotNull
  public static String headerStyle(@NotNull String header) {
    String output = center(header, MAX_WIDTH);
    return addNewLine(Ansi.ansi().fg(Colors.roundColor(0, 0)).bg(Color.MAGENTA)
        .bold().a(output.toUpperCase()).reset().toString());
  }

  /**
   * Formats a text with the title style.
   * Title style: Bright cyan font color, bold and uppercase.
   * @param title String with no ANSI sequence
   * @return ANSI String with formatted title
   */
  @NotNull
  public static String titleStyle(@NotNull String title) {
    return addNewLine(Ansi.ansi().bold().fgBrightCyan()
        .a("== " + title.toUpperCase()).reset().toString());
  }

  /**
   * Formats a text with the subtitle style.
   * Subtitle style: Default color and bold.
   * @param title String with no ANSI sequence
   * @return ANSI String with formatted subtitle
   */
  @NotNull
  public static String subtitleStyle(String title) {
    return addNewLine(Ansi.ansi().bold().a(title).reset().toString());
  }

  /**
   * Formats a text with the highlight style.
   * Highlight style: White rbg color.
   * @param text String with no ANSI sequence
   * @return ANSI String with formatted text
   */
  @NotNull
  public static String highlightStyle(String text) {
    return Ansi.ansi().fg(Colors.roundColor(0, 0))
        .a(text).reset().toString();
  }

  /**
   * Formats a message, input type and end into an input prompt.
   * Prompt style: Blue message, yellow input type, and bold end.
   * @param message Describes what the input is for
   * @param inputType Hints the type of data expected
   * @return ANSI String with formatted prompt
   */
  @NotNull
  public static String promptStyle(
      @NotNull String message, @NotNull String inputType) {
    return String.format(
        "%s %s: %s ",
        Ansi.ansi().fgBlue().a(message).reset().toString(),
        Ansi.ansi().fgYellow().a(inputType).reset().toString(),
        Ansi.ansi().bold().a(PROMPT_END).reset().toString());
  }

  /**
   * Formats a message into a confirmation prompt.
   * Confirmation style: White font and italic.
   * @return ANSI String with formatted prompt
   */
  public static String confirmationPromptStyle(@NotNull String message) {
    return String.format("%n%s: ",
        Ansi.ansi().fg(Colors.roundColor(0, 0))
            .a(Ansi.Attribute.ITALIC).a(message).reset().toString());
  }

  /**
   * Formats a message with the error style.
   * Error style: Bright red color and italic.
   * @param message String with no ANSI sequence
   * @return ANSI String with formatted message
   */
  public static String errorStyle(String message) {
    return Ansi.ansi().fgBrightRed().bold()
        .a(Ansi.Attribute.ITALIC).a(message).reset().toString();
  }

  /**
   * Formats a message with the success style.
   * Success tyle: Bright green color and italic.
   * @param message String with no ANSI sequence
   * @return ANSI String with formatted message
   */
  public static String successStyle(String message) {
    return Ansi.ansi().fgBrightGreen().bold()
        .a(Ansi.Attribute.ITALIC).a(message).reset().toString();
  }

  /**
   * Formats a message with the warning style.
   * Error style: Bright yello color and italic.
   * @param message String with no ANSI sequence
   * @return ANSI String with formatted message
   */
  public static String warningStyle(String message) {
    return Ansi.ansi().fgBrightYellow().bold()
        .a(Ansi.Attribute.ITALIC).a(message).reset().toString();
  }

  public static String formatWithColor(Ansi.Color color, String text) {
    return Ansi.ansi().fg(color).a(text).reset().toString();
  }

  public static int MAX_WIDTH() {
    return MAX_WIDTH;
  }

  /**
   * Adds a new line to the end of a String.
   * @return String with a new line at the end
   */
  @NotNull
  @Contract(pure = true)
  public static String addNewLine(String text) {
    return text + "\n";
  }
}
