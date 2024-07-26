package net.luuh.test.abstraction.modules;

import net.luuh.test.Helper;
import net.luuh.test.abstraction.database.DatabaseTable;
import net.luuh.test.database.DatabaseProvider;

public abstract class DatabaseModule<T extends DatabaseTable> extends Module {

    protected final T table;

    public DatabaseModule(Helper helper) {
        super(helper);

        DatabaseProvider databaseProvider = helper.getDatabaseProvider();

        this.table = registerTable(databaseProvider);

        databaseProvider.registerTable(table);
    }

    protected abstract T registerTable(DatabaseProvider databaseProvider);

    public T getTable() {
        return table;
    }
}
