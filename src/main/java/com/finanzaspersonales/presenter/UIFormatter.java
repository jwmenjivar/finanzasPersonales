package com.finanzaspersonales.presenter;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.io.Colors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Utility class that uses 'jansi' to format all the strings to UI elements.
 * All methods receive a string and returns a formatted string.
 */
public class UIFormatter {
  // width of the screen
  private static final int MAX_WIDTH = 80;

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
   * @param offset Must be less than the MAX_WIDTH
   * @return Formatted string with preceding spaces
   */
  @NotNull
  public static String textAlignLeft(@NotNull String text, int offset) {
    if (offset > 0) {
      if ((offset + text.length()) > MAX_WIDTH) {
        offset = MAX_WIDTH - text.length();
      }

      text += " ".repeat(offset);
    }

    return text;
  }

  /**
   * Aligns a text to the right adding empty spaces equal to the offset.
   * @param text String with no ANSI sequence
   * @param offset Must be less than the MAX_WIDTH
   * @return Formatted string with leading spaces
   */
  @NotNull
  public static String textAlignRight(@NotNull String text, int offset) {
    if (offset > 0) {
      if ((offset + text.length()) > MAX_WIDTH) {
        offset = MAX_WIDTH - text.length();
      }

      text = " ".repeat(offset) + text;
    }

    return addNewLine(text);
  }

  /**
   * Wraps text to the MAX_WIDTH.
   * @param text String with no ANSI sequence
   * @return String with multiple lines
   */
  @NotNull
  public static String wrapText(@NotNull String text) {
    if (text.length() > MAX_WIDTH) {
      StringBuilder temp = new StringBuilder();
      int start = 0;
      int end = MAX_WIDTH;

      while ((text.length() - MAX_WIDTH) > 0) {
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
  public static String menu(@NotNull List<MenuItem> menuItems) {
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

      String details = "\t"
          + Ansi.ansi().fg(Color.WHITE)
                .a(Ansi.Attribute.ITALIC)
                .a(s.getDescription()).reset().toString();
      menu.append(details).append("\n");
      count++;
    }

    return menu.toString();
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
    return addNewLine(
        Ansi.ansi().bold().fgBrightCyan().a("== " + title.toUpperCase()).reset().toString());
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
    return addNewLine(
        Ansi.ansi().fg(Colors.roundColor(0, 0)).a(text).reset().toString());
  }

  /**
   * Formats a message, input type and end into an input prompt.
   * @param message Describes what the input is for
   * @param inputType Hints the type of data expected
   * @param end Symbols to end the prompt
   * @return ANSI String with formatted prompt
   */
  @NotNull
  public static String promptStyle(
      @NotNull String message, @NotNull String inputType, @NotNull String end) {
    return String.format(
        "%s %s: %s ",
        Ansi.ansi().fgBlue().a(message).reset().toString(),
        Ansi.ansi().fgYellow().a(inputType).reset().toString(),
        Ansi.ansi().bold().a(end).reset().toString());
  }

  /**
   * Adds a new line to the end of a String.
   * @param text
   * @return String with a new line at the end
   */
  @NotNull
  @Contract(pure = true)
  public static String addNewLine(String text) {
    return text + "\n";
  }
}
