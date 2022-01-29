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
        //She creates an instance of the Class we are currently
        //Kind of recursive

        App application = new App();

        //Don't know what is this for
        final String toMainMenu = "Back to the main menu.";

        // Get an instance of view
        //HL Explanation of the line below
        //setView is a Lombock Set in line 25 ooc (of original code)
        // receives a View.getView() as parameter,
        // which invokes <through interface> the "RETURN" and "CONSTRUCTION of ViewImpl data type object",
        // That ViewImpl data type returned object, is also considered a View type, because ViewImpl implements View
        // is now set in the view field of application object.

        application.setView(View.getView());

        /* Create TransactionPresenter with all its operations */

        //She creates a sub-instance of presenter abstract class
       //using its TransactionPresenter subclass overloaded constructor
        //(This implies that all abstract fields and methods are inherited by the subclass)
        //(As matter of fact there's more fields and methods in the abstract than in the sub)
        //(which complies with a DRY rule)
        /*
        It's very curious, the abstract class has three methods.
            public Action present()
            public Void addOperational(overloaded); (this one looks like a setter)
            protected Action runOperation(overloaded)

            for further comments, refer to the Presenter.java abstract class
        */
        //The parameters of the constructor are:
        /*The View type object set above and,
        the name field, in this case transaction.*/
        //Both the object and name field are inherited from the Presenter abstract class
        //So the question now is, what is this transactionPresenter object going to be used for?
        //No answer yet... It is to be used later on main codeline 45 ooc
        Presenter transactionPresenter =
            new TransactionPresenter(application.getView(), "Transactions");




        //She now creates a CreateTransaction object type, called createTransaction. (Denisse States is an operational, and as matter of fact it is used as an operational down below)
        //In this case she did not use the abstract class. Why not?? Asked Denisse & gc
        //pending answer. Current guess, is TransactionData is the abstract class but...
        // it extends to another abstract class called ModelOperation
        //
        // A este constructor tambien le pasamos el View object type, que seteamos arriba
        //So the question now is, what is the createTransaction object for?
        //No answer yet
        CreateTransaction createTransaction = new CreateTransaction(application.getView());

        //this object was instantiated above, and we are now using one of its "inherited from Presenter" Super class ();
        // addOperational()
        //The argument of addOperational includes
        /*
            String for name, so we use an ENUM. then we select CREATE.
                y luego get name (que es un fieldd del enum en el enum que               obtenemos con lombok, y que se crea con el constructor del                 Enum)
            An Operational object type,
            Another String for description
         */
        //
        transactionPresenter.addOperational(
            OperationName.CREATE.getName(), createTransaction, "Create a new transaction.");



        UpdateTransaction updateTransaction = new UpdateTransaction(application.getView());
        transactionPresenter.addOperational(
            OperationName.UPDATE.getName(), updateTransaction, "Update an existing transaction.");
        ShowTransactions showTransactions = new ShowTransactions(application.getView());
        transactionPresenter.addOperational(
            OperationName.SHOW.getName(), showTransactions, "Show transactions.");
        DeleteTransaction deleteTransaction = new DeleteTransaction(application.getView());
        transactionPresenter.addOperational(
            OperationName.DELETE.getName(), deleteTransaction, "Delete existing transaction.");

        // Instantiate the menu presenter to add the back navigation operation
        Presenter menuPresenter = new MenuPresenter(application.getView(), "Menu");
        Navigation mainMenu = new Navigation(menuPresenter.getName());
        transactionPresenter.addOperational(
            OperationName.BACK.getName(), mainMenu, toMainMenu);

        /* Create CategoryPresenter with all its operations */
        UpdateCategory updateCategory = new UpdateCategory(application.getView());
        Presenter categoryPresenter = new CategoryPresenter(application.getView(), "Categories");
        CreateCategory createCategory = new CreateCategory(application.getView());
        categoryPresenter.addOperational(
            OperationName.CREATE.getName(), createCategory, "Create a new category.");
        DeleteCategory deleteCategory = new DeleteCategory(application.getView());
        categoryPresenter.addOperational(
            OperationName.UPDATE.getName(), updateCategory, "Update an existing category.");
        ShowCategories showCategories = new ShowCategories(application.getView());
        categoryPresenter.addOperational(
            OperationName.SHOW.getName(), showCategories, "Show categories.");
        categoryPresenter.addOperational(
            OperationName.DELETE.getName(), deleteCategory, "Delete existing category.");
        categoryPresenter.addOperational(
            OperationName.BACK.getName(), mainMenu, toMainMenu);

        /* Create BudgetPresenter with all its operations */
        Presenter budgetPresenter = new BudgetPresenter(application.getView(), "Budget");
        CreateBudget createBudget = new CreateBudget(application.getView());
        budgetPresenter.addOperational(
            OperationName.CREATE.getName(), createBudget, "Create a monthly budget.");
        DeleteBudget deleteBudget = new DeleteBudget(application.getView());
        budgetPresenter.addOperational(
            OperationName.DELETE.getName(), deleteBudget, "Disable the monthly budget.");
        budgetPresenter.addOperational(
            OperationName.BACK.getName(), mainMenu, toMainMenu);

        /* Create MenuPresenter with all its operations */
        Navigation navigateTransactions = new Navigation(transactionPresenter.getName());
        menuPresenter.addOperational(
            transactionPresenter.getName(), navigateTransactions, "Transaction operations.");
        Navigation navigateCategories = new Navigation(categoryPresenter.getName());
        menuPresenter.addOperational(
            categoryPresenter.getName(), navigateCategories, "Category operations.");
        Navigation navigateBudget = new Navigation(budgetPresenter.getName());
        menuPresenter.addOperational(
            budgetPresenter.getName(), navigateBudget, "Budget operations.");
        ExportOperation exportOperation = new ExportOperation(application.getView());
        menuPresenter.addOperational(
            OperationName.EXPORT.getName(), exportOperation, "Export existing transaction.");
        ExitApp exit = new ExitApp(application.getView());
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

    public void run() {
        // runs the menu presenter
        Action applicationAction = new Action(ActionType.NONE);

        while (!applicationAction.getType().equals(ActionType.EXIT)) {
            applicationAction = presenter.present();

            if (!applicationAction.getType().equals(ActionType.RELOAD)) {
                presenter = presenterHashMap.get(applicationAction.getNavigateTo());
            }
        }

        exit();
    }

    private static void startJansi() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    private void exit() {
        System.exit(0);
    }
}
