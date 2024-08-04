package net.luuh.descent.abstraction.database;

import net.luuh.descent.database.DatabaseProvider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class DatabaseTable {

    private final ExecutorService DATABASE_EXECUTOR = Executors.newFixedThreadPool(3, t -> {
        Thread thread = new Thread(t);
        thread.setName("verion-database");
        thread.setDaemon(false);

        return thread;
    });

    protected final DatabaseProvider databaseProvider;
    protected final String[] tableQueries;

    public DatabaseTable(DatabaseProvider databaseProvider, String... tableQueries) {
        this.databaseProvider = databaseProvider;
        this.tableQueries = tableQueries;
    }

    public String[] getTableQueries() {
        return tableQueries;
    }

    protected Connection getConnection() throws SQLException {
        return databaseProvider.getConnection();
    }

    protected CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, DATABASE_EXECUTOR);
    }

    protected <T> CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, DATABASE_EXECUTOR);
    }
}
