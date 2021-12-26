package com.finanzaspersonales.presenter;

import com.finanzaspersonales.view.MenuItem;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.io.Colors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UIFormatter {
  private static final int WIDTH = 80;

  private UIFormatter() { }

  @NotNull
  public static String center(@NotNull String text) {
    return center(text, WIDTH);
  }

  @NotNull
  public static String center(@NotNull String text, int width) {
    int spacing = (width - text.length()) / 2;
    if (spacing > 0) {
      return " ".repeat(spacing) + text + " ".repeat(spacing);
    }

    return addNewLine(text);
  }

  @NotNull
  public static String textAlignLeft(@NotNull String text, int offset) {
    if (offset > 0) {
      if ((offset + text.length()) > WIDTH) {
        offset = WIDTH - text.length();
      }

      text += " ".repeat(offset);
    }

    return addNewLine(text);
  }

  @NotNull
  public static String textAlignRight(@NotNull String text, int offset) {
    if (offset > 0) {
      if ((offset + text.length()) > WIDTH) {
        offset = WIDTH - text.length();
      }

      text = " ".repeat(offset) + text;
    }

    return addNewLine(text);
  }

  @NotNull
  public static String formatText(@NotNull String text) {
    if (text.length() > WIDTH) {
      String temp = "";
      int start = 0;
      int end = WIDTH;

      while ((text.length() - WIDTH) > 0) {
        temp += text.substring(start, end) + "\n";
        text = text.substring(end);
      }

      text = temp + text;
    }

    return addNewLine(text);
  }

  @NotNull
  public static String menu(@NotNull List<MenuItem> menuItems) {
    int count = 1;
    StringBuilder list = new StringBuilder();
    for (MenuItem s : menuItems) {
      String element = String.format("%d. %s", count, s.getItem());
      list.append(element);
      String details = "\t" + Ansi.ansi().fg(Color.WHITE).a(s.getDescription()).reset().toString();
      list.append(details).append("\n");
      count++;
    }

    return addNewLine(list.toString());
  }

  @NotNull
  public static String headerStyle(@NotNull String header) {
    String output = center(header, WIDTH);
    return addNewLine(Ansi.ansi().fg(Colors.roundColor(0, 0)).bg(Color.MAGENTA)
        .bold().a(output.toUpperCase()).reset().toString());
  }

  @NotNull
  public static String titleStyle(@NotNull String title) {
    return addNewLine(
        Ansi.ansi().bold().fgBrightCyan().a("== " + title.toUpperCase()).reset().toString());
  }

  @NotNull
  public static String subtitleStyle(String title) {
    return addNewLine(Ansi.ansi().bold().a(title).reset().toString());
  }

  @NotNull
  public static String highlightStyle(String text) {
    return addNewLine(
        Ansi.ansi().fg(Colors.roundColor(0, 0)).a(text).reset().toString());
  }

  @NotNull
  @Contract(pure = true)
  public static String addNewLine(String text) {
    return text + "\n";
  }
}
