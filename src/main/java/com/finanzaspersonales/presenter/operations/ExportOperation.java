package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Export;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExportOperation extends Operation {

  public ExportOperation(MainView view) {
    super(view, "Export transactions", "Exporting transactions...");
  }

  @Override
  protected void operation() throws IOException {
//    view.appendWithoutNewline(UIFormatter.promptStyle("Enter file name:", SimpleInput.TEXT));
//    String fileName = SimpleInput.readString();

    Export.exportAllTransactions();
    view.appendWithNewline(UIFormatter.successStyle("Exported transactions."));
  }
}
