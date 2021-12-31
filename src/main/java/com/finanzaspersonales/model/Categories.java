package com.finanzaspersonales.model;

import org.jetbrains.annotations.NotNull;

public class Categories {

  private Categories() { }

  @NotNull
  public static Category create(@NotNull Transaction.TransactionType type,
                                @NotNull String name, String description) {
    if (description == null) {
      description = "";
    }

    Category c = new Category(type, name, description);
    Database.db().saveCategory(c);

    return c;
  }

  public static void update(Category updatedCategory) {
    Database.db().updateCategory(updatedCategory);
  }

  public static Category[] getAll() {
    return Database.db().getAllCategories();
  }

  public static Category getByName(@NotNull String name) {
    return Database.db().getCategoryByName(name);
  }

  public static Category[] getByType(@NotNull Transaction.TransactionType type) {
    return Database.db().getCategoriesByType(type);
  }

  public static boolean exists(@NotNull String name) {
    if (name.isEmpty())
      return false;

    return Database.db().categoryExists(name);
  }

  public static boolean hasTransactions(@NotNull String name) {
    if (name.isEmpty())
      return false;

    return Database.db().categoryHasTransactions(name);
  }

  public static void delete(@NotNull String name) {
    if (name.isEmpty()) {
      Database.db().deleteCategory(name);
    }
  }

  public static void deleteAll() {
    Database.db().deleteAllCategories();
  }
}
