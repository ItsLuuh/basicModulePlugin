package net.luuh.test.abstraction.database;

import net.luuh.test.abstraction.modules.DatabaseModule;
import net.luuh.test.database.DatabaseProvider;

public class ModuleDatabaseTable<T extends DatabaseModule<?>> extends DatabaseTable {

    protected final T module;

    public ModuleDatabaseTable(T module, DatabaseProvider databaseProvider, String... tableQueries) {
        super(databaseProvider, tableQueries);
        this.module = module;
    }
}
