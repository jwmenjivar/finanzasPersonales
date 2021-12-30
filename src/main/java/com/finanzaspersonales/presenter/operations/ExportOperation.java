package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Export;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

public class ExportOperation extends Operation {

  public ExportOperation(View view) {
    super(view, "Export transactions", "Exporting transactions");
  }

  @Override
  protected void operation() {
//    view.appendWithoutNewline(UIFormatter.promptStyle("Enter file name:", SimpleInput.TEXT));
//    String fileName = SimpleInput.readString();

    Export.exportAllTransactions();
    view.appendWithNewline(UIFormatter.successStyle("Exported transactions."));
  }
}
