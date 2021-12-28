package com.finanzaspersonales.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class FakeDB implements Database {
  private List<Transaction> transactions;
  private List<Category> categories;
  private Random random;
  private static FakeDB instance = null;

  private FakeDB() { }

  public void connect() {
    this.random = new Random();
    populateCategories();
    populateTransactions();
  }

  @Override
  public Category[] getAllCategories() {
    return categories.toArray(new Category[0]);
  }

  @Override
  public Category[] getCategoriesByType(Transaction.TransactionType type) {
    return categories.stream().filter(category ->
        category.getTransactionType().equals(type)).toArray(Category[]::new);
  }

  @Override
  public Transaction[] getAllTransactions() {
    return transactions.toArray(new Transaction[0]);
  }

  @Override
  public Transaction[] getTransactionsByDate(LocalDate date) {
    return transactions.stream().filter(transaction ->
        transaction.getDate().isEqual(date)).toArray(Transaction[]::new);
  }

  @Override
  public Transaction getTransactionByID(String id) {
    Optional<Transaction> t = transactions.stream().filter(
        transaction -> transaction.getUniqueID().equals(id)).findFirst();
    return t.orElse(null);
  }

  @Override
  public void saveTransaction(Transaction t) {
    transactions.add(t);
    transactions.sort(Comparator.comparing(Transaction::getDate));
  }

  @Override
  public void updateTransaction(Transaction t) {
    /* Doesn't really do anything lol */
  }

  @Override
  public void deleteTransaction(String id) {
    Optional<Transaction> t = transactions.stream()
        .filter(transaction -> transaction.getUniqueID().equals(id)).findFirst();

    t.ifPresent(transaction -> transactions.remove(transaction));
  }

  @Override
  public void deleteAllTransactions() {
    transactions.clear();
  }

  @Override
  public boolean transactionExists(String id) {
    return transactions.stream().anyMatch(
        transaction -> transaction.getUniqueID().equals(id));
  }

  public static FakeDB getInstance() {
    if (instance == null) {
      instance = new FakeDB();
      instance.connect();
    }

    return instance;
  }

  /**
   * Creates random categories to fake a DB.
   */
  private void populateCategories() {
    this.categories = Arrays.asList(
        new Category(Transaction.TransactionType.INCOME, "Salario"),
        new Category(Transaction.TransactionType.INCOME, "Mesada"),
        new Category(Transaction.TransactionType.INCOME, "Bonus"),
        new Category(Transaction.TransactionType.INCOME, "Inversión"),
        new Category(Transaction.TransactionType.INCOME, "Regalo"),
        new Category(Transaction.TransactionType.EXPENSE, "Mantenimiento"),
        new Category(Transaction.TransactionType.EXPENSE, "Electricidad"),
        new Category(Transaction.TransactionType.EXPENSE, "Ahorro"),
        new Category(Transaction.TransactionType.EXPENSE, "Préstamos"),
        new Category(Transaction.TransactionType.EXPENSE, "Internet"),
        new Category(Transaction.TransactionType.EXPENSE, "Ropa"),
        new Category(Transaction.TransactionType.EXPENSE, "Gimnasio"),
        new Category(Transaction.TransactionType.EXPENSE, "Salud"),
        new Category(Transaction.TransactionType.EXPENSE, "Belleza"),
        new Category(Transaction.TransactionType.EXPENSE, "Comida"));
  }

  /**
   * Creates random transactions to fake a DB.
   */
  private void populateTransactions() {
    this.transactions = new ArrayList<>();

    Category[] incomeCategories = getCategoriesByType(Transaction.TransactionType.INCOME);
    Category[] expenseCategories = getCategoriesByType(Transaction.TransactionType.EXPENSE);

    // calculate the date 20 days ago
    long aDay = TimeUnit.DAYS.toMillis(1);
    Date now = new Date();
    Date ago = new Date(now.getTime() - aDay * 20);

    for (int i = 0; i < 40; i++) {
      Transaction t;
      if (this.random.nextInt(2) == 0) {
        t = Transaction.makeIncomeTransaction();
        t.setCategory(incomeCategories[this.random.nextInt(incomeCategories.length)]);
      } else {
        t = Transaction.makeExpenseTransaction();
        t.setCategory(expenseCategories[this.random.nextInt(expenseCategories.length)]);
      }
      t.setDate(randomDateBetween(ago, now));
      t.setDescription("Buena descripcion");
      t.setAmount(this.random.nextDouble() * 10000);

      this.transactions.add(t);
    }

    this.transactions.sort(Comparator.comparing(Transaction::getDate));
  }

  /**
   * Returns a random date between the start and end (inclusive) date.
   * src: https://www.baeldung.com/java-random-dates
   */
  @NotNull
  private LocalDate randomDateBetween(@NotNull Date start, @NotNull Date end) {
    long startMillis = start.getTime();
    long endMillis = end.getTime() + TimeUnit.DAYS.toMillis(1);
    long randomMillisSinceEpoch = ThreadLocalRandom
        .current()
        .nextLong(startMillis, endMillis);

    return new Date(randomMillisSinceEpoch).toInstant()
        .atZone(ZoneId.systemDefault()).toLocalDate();
  }
}
