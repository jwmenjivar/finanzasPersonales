package com.finanzaspersonales.presenter.ui;

import com.finanzaspersonales.model.Category;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class that uses 'jansi' to format the way categories are displayed
 * on screen.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class CategoryFormatter extends DataFormatter {
  private static final int DETAIL_SPACE = 15;
  public static final String ID_H = "Category ID";
  public static final String NAME_H = "Name";
  public static final String TYPE_H = "Type";
  public static final String DESCRIPTION_H = "Description";

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
}
