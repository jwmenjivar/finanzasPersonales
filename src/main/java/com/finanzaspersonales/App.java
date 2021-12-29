package com.finanzaspersonales;

import com.finanzaspersonales.presenter.Action;
import com.finanzaspersonales.presenter.Presenter;
import com.finanzaspersonales.view.MainView;
import org.fusesource.jansi.AnsiConsole;

/**
 * Main class and entry point.
 * Starts the application and initializes the first view.
 */
public class App 
{
    public static void main( String[] args )
    {
        startJansi();

        // instantiate and initialize first view
        MainView mainView = MainView.getMainView();
        mainView.initialize();
        MainView activeView = mainView;
        Presenter activePresenter = activeView.getPresenter();

        // start main app loop
        Action action = activePresenter.chooseOperation();

        while (action.actionType != Action.ActionType.EXIT) {
            if (action.actionType == Action.ActionType.NAVIGATION) {
                activeView = action.nextView;
                activeView.initialize();
                activePresenter = activeView.getPresenter();
            }

            action = activePresenter.chooseOperation();
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
