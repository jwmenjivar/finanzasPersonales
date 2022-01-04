package com.finanzaspersonales.presenter.operations;

import com.finanzaspersonales.model.Export;
import com.finanzaspersonales.model.Mail;
import com.finanzaspersonales.model.Transactions;
import com.finanzaspersonales.presenter.input.DataInput;
import com.finanzaspersonales.presenter.input.MenuInput;
import com.finanzaspersonales.presenter.input.SimpleInput;
import com.finanzaspersonales.presenter.ui.UIFormatter;
import com.finanzaspersonales.view.View;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ExportOperation extends Operation {

  public ExportOperation(View view) {
    super(view, "Export transactions", "Exporting transactions");
  }

  @Override
  protected void operation() {
    view.warning("The file will be exported to " + System.getProperty("user.dir"));

    StringBuilder existingYears = new StringBuilder();
    HashMap<Integer, Long> years = Transactions.getYearsWithTransactionsCount();
    existingYears.append(
        UIFormatter.formatWithColor(Ansi.Color.CYAN, "\nYears with transactions:\n"));
    for (Map.Entry<Integer, Long> yc : years.entrySet()) {
      existingYears.append(String.format("[Year: %d (count: %d)]%n", yc.getKey(), yc.getValue()));
    }
    view.append(existingYears.toString());
    int year = DataInput.inputYear(view);

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
          String to = DataInput.inputEmail(view);

          view.append(UIFormatter.highlightStyle("Sending email..."));
          Mail send = new Mail();
          send.sendExportFile(System.getProperty("user.dir") + "/" + fileName, to);

          view.success("Message sent");
        }
      } catch (IOException e) {
        view.error(e.getMessage());
      }
    } else {
      view.error("Input year has no transactions.");
    }
  }
}
