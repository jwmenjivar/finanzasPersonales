package com.finanzaspersonales;

import com.finanzaspersonales.model.Category;
import com.finanzaspersonales.model.Transaction;
import com.finanzaspersonales.model.TransactionType;
import com.finanzaspersonales.presenter.Action;
import com.finanzaspersonales.presenter.Presenter;
import com.finanzaspersonales.view.MainView;
import org.fusesource.jansi.AnsiConsole;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static List<Transaction> testTransactions;
    public static List<Category> expenseCategories;
    public static List<Category> incomeCategories;

    public static MainView activeView;
    public static Presenter activePresenter;

    public static void main( String[] args )
    {
        startJansi();

        populateCategories();
        populateTransactions();

        MainView mainView = MainView.getMainView();
        mainView.initialize();
        activeView = mainView;
        activePresenter = activeView.getPresenter();

        Action action = activePresenter.handleInput();

        while (action.actionType != Action.ActionType.EXIT) {
            if (action.actionType == Action.ActionType.NAVIGATION) {
                activeView = action.nextView;
                activeView.initialize();
                activePresenter = activeView.getPresenter();
            }

            action = activePresenter.handleInput();
        }

        exit();
    }

    public static void populateCategories() {
        Category c = new Category();
        c.setTransactionType(TransactionType.INCOME);
        c.setName("Salario");
        Category c1 = new Category();
        c1.setTransactionType(TransactionType.INCOME);
        c1.setName("Mesada");

        incomeCategories = Arrays.asList(c, c1);

        Category c2 = new Category();
        c2.setTransactionType(TransactionType.EXPENSE);
        c2.setName("Mantenimiento");
        Category c3 = new Category();
        c3.setTransactionType(TransactionType.EXPENSE);
        c3.setName("Electricidad");
        Category c4 = new Category();
        c4.setTransactionType(TransactionType.EXPENSE);
        c4.setName("Comida");

        expenseCategories = Arrays.asList(c2, c3, c4);
    }

    public static void populateTransactions() {
        Random random = new Random();
        testTransactions = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Transaction t;
            if (random.nextInt(2) == 0) {
                t = Transaction.makeIncomeTransaction();
                t.setCategory(incomeCategories.get(random.nextInt(incomeCategories.size())));
            } else {
                t = Transaction.makeExpenseTransaction();
                t.setCategory(incomeCategories.get(random.nextInt(incomeCategories.size())));
            }
            t.setDate(new Date());
            t.setDescription("Buena descripcion");
            t.setAmount(random.nextDouble() * 10000);

            testTransactions.add(t);
        }
    }

    public static void startJansi() {
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
    }

    public static void exit() {
        System.exit(0);
    }
}
