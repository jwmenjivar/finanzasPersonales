package com.finanzaspersonales.presenter.ui;

import com.finanzaspersonales.model.Category;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class that uses 'jansi' to format the way categories are displayed
 * on screen.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class CategoryFormatter extends DataFormatter {
  private static final int NUMBER_SPACE = 5;
  private static final int NAME_SPACE = 15;
  private static final int TYPE_SPACE = 10;
  private static final int TEXT_SPACE = 15;
  private static final int DETAIL_SPACE = 15;
  public static final String ID_H = "Category ID";
  public static final String NAME_H = "Name";
  public static final String TYPE_H = "Type";
  public static final String DESCRIPTION_H = "Description";
  private static final String NO_CATEGORIES = "<No categories>";

  private CategoryFormatter() { }

  /**
   * Formats a category into a multiline String with the full data.
   * @return Multiline ANSI String with the category details
   */
  @NotNull
  public static String categoryDetailed(@NotNull Category category) {
    String formatted = "";
    String detailFormat = "%s %s\n";
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(Ansi.Attribute.UNDERLINE)
            .a(formatInlineText(ID_H + ":", DETAIL_SPACE)).reset().toString(),
        category.getUniqueID());
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(TYPE_H + ":", DETAIL_SPACE)).reset().toString(),
        formatTextByType(category.getType().name(), category.getType()));
    formatted += String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(NAME_H + ":", DETAIL_SPACE)).reset().toString(),
        category.getName());
    formatted += UIFormatter.wrapText(String.format(
        detailFormat,
        Ansi.ansi().bold().fgBrightDefault().a(
            formatInlineText(DESCRIPTION_H+ ":", DETAIL_SPACE)).reset().toString(),
        category.getDescription()));

    return formatted;
  }

  /**
   * Formats a list of categories into a table. If no categories are provided,
   * shows a placeholder message.
   * Data order: # > Name > Type > Description
   * @return Multiline ANSI String with a table of categories
   */
  @NotNull
  public static String categoryTable(@NotNull Category[] categories) {
    StringBuilder formatted = new StringBuilder();

    // format the header
    formatted.append(
        // # date amount type category description
        // # > Name > Type > Description
        Ansi.ansi().bg(Ansi.Color.BLACK).a(String.format(
                "%s %s %s %s",
                Ansi.ansi().bold().fgBright(Ansi.Color.WHITE).a(
                    formatInlineText("#", NUMBER_SPACE)),
                Ansi.ansi().bold().a(UIFormatter.center(NAME_H.toUpperCase(), NAME_SPACE)),
                Ansi.ansi().bold().a(UIFormatter.center(TYPE_H.toUpperCase(), TYPE_SPACE)),
                Ansi.ansi().bold().a(UIFormatter.center(DESCRIPTION_H.toUpperCase(), TEXT_SPACE))))
            .reset().toString()).append("\n");

    if (categories.length > 0) {
      int count = 1;
      for (Category c : categories) {
        formatted.append(String.format("%s %s %s %s%n",
            Ansi.ansi().bold().a(
                formatInlineText(String.valueOf(count), NUMBER_SPACE)).reset().toString(),
            formatInlineText(c.getName(), NAME_SPACE),
            formatInlineText(c.getType().name(), TYPE_SPACE),
            formatInlineText(c.getDescription(), TEXT_SPACE)));
        count++;
      }
    } else {
      formatted.append(NO_CATEGORIES).append("\n");
    }

    return formatted.toString();
  }

  @NotNull
  @Contract(pure = true)
  public static String categoriesDetailed(@NotNull Category[] categories) {
    StringBuilder formatted = new StringBuilder();

    if (categories.length > 0) {
      for (Category c : categories) {
        formatted.append(categoryDetailed(c));
      }
    } else {
      formatted.append(NO_CATEGORIES).append("\n");
    }

    return formatted.toString();
  }
}
