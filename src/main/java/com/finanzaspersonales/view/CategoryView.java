package com.finanzaspersonales.view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface CategoryView extends MainView {

  /**
   * Returns a new Category implementation.
   * @return
   */
  @NotNull
  @Contract(" -> new")
  static CategoryView getCategoryView() {
    return new CategoryViewImpl();
  }
}
