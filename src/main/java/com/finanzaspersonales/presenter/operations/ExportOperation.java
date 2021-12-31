package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Export;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.MainView;

import java.io.IOException;

public class ExportOperation extends Operation {

  public ExportOperation(MainView view) {
    super(view, "Export transactions", "Exporting transactions...");
  }

  @Override
  protected void operation() throws IOException {
    view.appendWithNewline(
        UIFormatter.warningStyle("The file will be exported to " + System.getProperty("user.dir")));
    view.appendWithoutNewline(UIFormatter.promptStyle("Enter file name:", SimpleInput.TEXT));
    String fileName = SimpleInput.readString();

    try {
      Export.exportAllTransactions(fileName);
      view.appendWithNewline(UIFormatter.successStyle("Exported transactions."));
    } catch (IOException e) {
      view.appendWithoutNewline(UIFormatter.errorStyle(e.getMessage()));
    }
  }
}
