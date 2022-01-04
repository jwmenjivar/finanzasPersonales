package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Export;
import com.finanzaspersonales.model.Mail;
import com.finanzaspersonales.model.Transactions;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;

import java.io.IOException;
import java.time.LocalDate;

public class ExportOperation extends Operation {

  public ExportOperation(View view) {
    super(view, "Export transactions", "Exporting transactions");
  }

  @Override
  protected void operation() {
    view.warning("The file will be exported to " + System.getProperty("user.dir"));

    view.append(UIFormatter.highlightStyle("Insert the year to export:"));
    view.prompt("Enter year to export", SimpleInput.NUMBER);
    int year = SimpleInput.readInteger();

    if (Transactions.yearHasTransactions(year)) {
      view.append(UIFormatter.highlightStyle("Default name 'Export [yyyy-mm-dd]':"));
      view.prompt("Enter file name:", SimpleInput.TEXT);

      String fileName = SimpleInput.readString();
      fileName = fileName.isEmpty() ? "Export " + LocalDate.now() : fileName;
      fileName += ".xls";

      try {
        Export.exportTransactionsByYear(fileName, year);
        view.success("Exported transactions\n");

        view.append(UIFormatter.highlightStyle("Send export as email?"));
        boolean choice = MenuInput.handleYesNo(view);

        if (choice) {
          view.prompt("Enter email: ", SimpleInput.TEXT);
          String to = SimpleInput.readString();

          view.append(UIFormatter.highlightStyle("Sending email..."));
          Mail send = new Mail();
          send.sendExportFile(System.getProperty("user.dir") + "/" + fileName, to);

          view.success("Message sent");
        }
      } catch (IOException e) {
        view.error(e.getMessage());
      }
    } else {
      view.error("This year has no transactions.");
    }
  }
}
