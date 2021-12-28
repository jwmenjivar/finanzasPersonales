package com.finanzaspersonales.view;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ReportView extends MainView {

  /**
   * Returns a new ReportView implementation.
   * @return
   */
  @NotNull
  @Contract(" -> new")
  static ReportView getReportView() {
    return new ReportViewImpl();
  }
}
