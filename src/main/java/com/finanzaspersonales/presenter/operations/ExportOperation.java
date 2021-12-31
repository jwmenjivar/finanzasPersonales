package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Export;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.view.View;

import java.io.IOException;

public class ExportOperation extends Operation {

  public ExportOperation(View view) {
    super(view, "Export transactions", "Exporting transactions");
  }

  @Override
  protected void operation() {
    view.warning("The file will be exported to " + System.getProperty("user.dir"));
    view.prompt("Enter file name:", SimpleInput.TEXT);
    String fileName = SimpleInput.readString();

    try {
      Export.exportAllTransactions(fileName);
      view.success("Exported transactions.");
    } catch (IOException e) {
      view.error(e.getMessage());
    }
  }
}
