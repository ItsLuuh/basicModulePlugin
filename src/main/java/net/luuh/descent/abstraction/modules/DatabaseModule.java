package net.luuh.descent.abstraction.modules;

import net.luuh.descent.Helper;
import net.luuh.descent.abstraction.database.DatabaseTable;
import net.luuh.descent.database.DatabaseProvider;

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
