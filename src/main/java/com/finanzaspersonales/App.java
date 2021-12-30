package com.finanzaspersonales;

import com.finanzaspersonales.presenter.*;
import com.finanzaspersonales.view.View;
import org.fusesource.jansi.AnsiConsole;

import java.util.EnumMap;

/**
 * Main class and entry point.
 * Starts the application and initializes the first view.
 * Then, runs a loop that manages which view is displayed.
 * Presenters send Actions with an instruction on which view to
 * load next. When the user chooses the Action.EXIT the loop ends
 * and the application exits.
 */
public class App 
{
    public static void main( String[] args )
    {
        startJansi();

        // Get an instance of the view
        View mainView = View.getView();

        // Create all the presenters and inject them the view
        MenuPresenter menuPresenter = new MenuPresenter(mainView);
        TransactionPresenter transactionPresenter = new TransactionPresenter(mainView);
        CategoryPresenter categoryPresenter = new CategoryPresenter(mainView);
        BudgetPresenter budgetPresenter = new BudgetPresenter(mainView);
        ReportPresenter reportPresenter = new ReportPresenter(mainView);

        // Set the MenuPresenter as the active presenter
        Presenter activePresenter = menuPresenter;
        // Create map of (Action, Presenter) to change between presenters easily
        EnumMap<Action, Presenter> presentersMap = new EnumMap<>(Action.class);
        presentersMap.put(Action.MENU, menuPresenter);
        presentersMap.put(Action.TRANSACTION, transactionPresenter);
        presentersMap.put(Action.CATEGORY, categoryPresenter);
        presentersMap.put(Action.BUDGET, budgetPresenter);
        presentersMap.put(Action.REPORT, reportPresenter);

        // Start main app loop
        Action action = Action.MENU;
        while (action != Action.EXIT) {
            // Present always ends by returning an action prompted by the user
            action = activePresenter.present();

            // If the action is not RELOAD, then it must be a presenter change
            if (action != Action.RELOAD) {
                activePresenter = presentersMap.get(action);
            }
        }
        exit();
    }

    public static void startJansi() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    public static void exit() {
        System.exit(0);
    }
}
