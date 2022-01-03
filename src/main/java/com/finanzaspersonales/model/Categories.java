package com.finanzaspersonales.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Performs all the category Database interactions.
 * @author denisse
 * @version 1.0
 * @since 1.0
 */
public class Categories {

  private Categories() { }

  /**
   * Creates a new category using the provided values.
   * @return A new Category instance.
   */
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

  /**
   * Updates all the values of a category.
   * @param updatedCategory Category with the updates.
   */
  public static void update(Category updatedCategory) {
    Database.db().updateCategory(updatedCategory);
  }

  /**
   * @return An array of all the recorded categories.
   */
  public static Category[] getAll() {
    return Database.db().getAllCategories();
  }

  /**
   * @return All the categories of the provided type.
   */
  public static Category[] getByType(@NotNull Transaction.TransactionType type) {
    return Database.db().getCategoriesByType(type);
  }

  /**
   * Given a name, returns a category.
   * @return The Category instance or null if it doesn't exist.
   */
  @Nullable
  public static Category getByName(@NotNull String name) {
    if (exists(name)) {
      return Database.db().getCategoryByName(name);
    }

    return null;
  }

  /**
   * Deletes an existing category from the DB.
   * If the name doesn't exist, it does nothing.
   */
  public static void delete(@NotNull String name) {
    if (!name.isEmpty()) {
      Database.db().deleteCategory(name);
    }
  }

  /**
   * Deletes all the recorded Categories from the DB.
   */
  public static void deleteAll() {
    Database.db().deleteAllCategories();
  }

  /**
   * Verifies if a there is a recorded Category with the provided name.
   */
  public static boolean exists(@NotNull String name) {
    if (name.isEmpty())
      return false;

    return Database.db().categoryExists(name);
  }

  /**
   * Verifies if a Category has transactions associated to it.
   */
  public static boolean hasTransactions(@NotNull String name) {
    if (name.isEmpty())
      return false;

    return Database.db().countTransactionsByCategory(name) > 0;
  }
}
