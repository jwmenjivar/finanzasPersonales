package com.finanzaspersonales;

import com.finanzaspersonales.presenter.*;
import com.finanzaspersonales.presenter.operations.*;
import com.finanzaspersonales.presenter.operations.category.CreateCategory;
import com.finanzaspersonales.presenter.operations.category.DeleteCategory;
import com.finanzaspersonales.presenter.operations.category.ShowCategories;
import com.finanzaspersonales.presenter.operations.category.UpdateCategory;
import com.finanzaspersonales.presenter.operations.transaction.CreateTransaction;
import com.finanzaspersonales.presenter.operations.transaction.DeleteTransaction;
import com.finanzaspersonales.presenter.operations.transaction.ShowTransactions;
import com.finanzaspersonales.presenter.operations.transaction.UpdateTransaction;
import com.finanzaspersonales.view.View;
import lombok.Getter;
import lombok.Setter;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Main class and entry point.
 */
public class App 
{
    @Setter @Getter
    private View view = View.getView();
    @Getter
    private Presenter presenter = null;
    @Setter @Getter
    private HashMap<String, Presenter> presenterHashMap;

    public static void main(String[] args) {
        startJansi();

        App application = new App();
        final String toMainMenu = "Back to the main menu.";

        // Get an instance of the view
        application.setView(View.getView());
        Presenter menuPresenter = new MenuPresenter(application.getView(), "Menu");
        Navigation mainMenu = new Navigation(menuPresenter.getName());

        CreateTransaction createTransaction = new CreateTransaction(application.getView());
        UpdateTransaction updateTransaction = new UpdateTransaction(application.getView());
        DeleteTransaction deleteTransaction = new DeleteTransaction(application.getView());
        ShowTransactions showTransactions = new ShowTransactions(application.getView());
        Presenter transactionPresenter =
            new TransactionPresenter(application.getView(), "Transactions");
        transactionPresenter.addOperational(
            OperationName.CREATE.getName(), createTransaction, "Create a new transaction.");
        transactionPresenter.addOperational(
            OperationName.UPDATE.getName(), updateTransaction, "Update an existing transaction.");
        transactionPresenter.addOperational(
            OperationName.SHOW.getName(), showTransactions, "Show transactions.");
        transactionPresenter.addOperational(
            OperationName.DELETE.getName(), deleteTransaction, "Delete existing transaction.");
        transactionPresenter.addOperational(
            OperationName.BACK.getName(), mainMenu, toMainMenu);

        CreateCategory createCategory = new CreateCategory(application.getView());
        UpdateCategory updateCategory = new UpdateCategory(application.getView());
        DeleteCategory deleteCategory = new DeleteCategory(application.getView());
        ShowCategories showCategories = new ShowCategories(application.getView());
        Presenter categoryPresenter = new CategoryPresenter(application.getView(), "Category");
        categoryPresenter.addOperational(
            OperationName.CREATE.getName(), createCategory, "Create a new category.");
        categoryPresenter.addOperational(
            OperationName.UPDATE.getName(), updateCategory, "Update an existing category.");
        categoryPresenter.addOperational(
            OperationName.SHOW.getName(), showCategories, "Show categories.");
        categoryPresenter.addOperational(
            OperationName.DELETE.getName(), deleteCategory, "Delete existing category.");
        categoryPresenter.addOperational(
            OperationName.BACK.getName(), mainMenu, toMainMenu);

        CreateBudget createBudget = new CreateBudget(application.getView());
        DeleteBudget deleteBudget = new DeleteBudget(application.getView());
        Presenter budgetPresenter = new BudgetPresenter(application.getView(), "Budget");
        budgetPresenter.addOperational(
            OperationName.CREATE.getName(), createBudget, "Create a monthly budget.");
        budgetPresenter.addOperational(
            OperationName.DELETE.getName(), deleteBudget, "Disable the monthly budget.");
        budgetPresenter.addOperational(
            OperationName.BACK.getName(), mainMenu, toMainMenu);

        // Create all the presenters and inject them the view
        Navigation navigateTransactions = new Navigation(transactionPresenter.getName());
        Navigation navigateCategories = new Navigation(categoryPresenter.getName());
        Navigation navigateBudget = new Navigation(budgetPresenter.getName());
        ExitApp exit = new ExitApp(application.getView());

        menuPresenter.addOperational(
            transactionPresenter.getName(), navigateTransactions, "Transaction operations.");
        menuPresenter.addOperational(
            categoryPresenter.getName(), navigateCategories, "Category operations.");
        menuPresenter.addOperational(
            budgetPresenter.getName(), navigateBudget, "Budget operations.");
        menuPresenter.addOperational(
            "Exit", exit, "Exit application.");

        // Create map of (Action, Presenter) to change between presenters easily
        application.setPresenterHashMap(new HashMap<>());
        application.getPresenterHashMap().put(menuPresenter.getName(), menuPresenter);
        application.getPresenterHashMap()
            .put(transactionPresenter.getName(), transactionPresenter);
        application.getPresenterHashMap().put(categoryPresenter.getName(), categoryPresenter);
        application.getPresenterHashMap().put(budgetPresenter.getName(), budgetPresenter);

        // Set the MenuPresenter as the active presenter
        application.presenter = menuPresenter;

        application.run();
    }

    public static void startJansi() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    public void run() {
        // runs the menu presenter
        String applicationAction = "";

        while (!applicationAction.equals("EXIT")) {
            applicationAction = runPresenter();

            if (!applicationAction.equals("RELOAD")) {
                presenter = presenterHashMap.get(applicationAction);
            }
        }

        exit();
    }

    public void exit() {
        System.exit(0);
    }

    public String runPresenter() {
        return presenter.present();
    }

    public void setPresenter(@NotNull String name) {
        presenter = presenterHashMap.get(name);
    }
}
