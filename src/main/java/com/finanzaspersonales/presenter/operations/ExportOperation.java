package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Export;
import com.finanzaspersonales.model.Mail;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

import java.io.IOException;
import java.time.LocalDate;

public class ExportOperation extends Procedure {

  public ExportOperation(View view) {
    super(view, "Export transactions", "Exporting transactions");
  }

  @Override
  protected void operation() {
    view.warning("The file will be exported to " + System.getProperty("user.dir"));
    view.prompt("Enter file name:", SimpleInput.TEXT);
    String fileName = SimpleInput.readString();
    fileName = fileName.isEmpty() ? "Export " + LocalDate.now() : fileName;
    fileName += ".xls";

    try {
      Export.exportTransactionsByYear(fileName, LocalDate.now().getYear());
      view.success("Exported transactions");

      view.append(UIFormatter.highlightStyle("Send export as email?")+"\n");
      boolean choice = MenuInput.handleYesNo(view);

      if (choice) {
        view.prompt("Enter email: ", SimpleInput.TEXT);
        String to = SimpleInput.readString();

        view.append(UIFormatter.highlightStyle("Sending email...")+"\n");
        Mail send = new Mail();
        send.sendExportFile(System.getProperty("user.dir") + "/" + fileName, to);

        view.success("Message sent");
      }
    } catch (IOException e) {
      view.error(e.getMessage());
    }
  }
}
