package net.luuh.mmorpg.database.tables.players;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.util.Properties;

public class HikariProvider {

    private final String host;
    private final String port;
    private final String database;
    private final String user;
    private final String password;

    private HikariDataSource dataSource;
    private final JavaPlugin plugin;

    public <T extends JavaPlugin> HikariProvider(T plugin) {
        this.plugin = plugin;

        Configuration config = this.plugin.getConfig();
        String key = "database.";
        this.host = config.getString(key + "host");
        this.port = config.getString(key + "port");
        this.database = config.getString(key + "databaseName");
        this.user = config.getString(key + "userName");
        this.password = config.getString(key + "password");
    }
    
    public void enable() throws IllegalStateException {
        this.plugin.getLogger().info("connecting hikari...");

        HikariConfig config = getHikariConfig();

        config.setPoolName("#verion-pool");
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("allowMultiQueries", true);
        config.setMaximumPoolSize(5);

        this.dataSource = new HikariDataSource(config);

        this.plugin.getLogger().info("connected hikari pool");
    }

    @NotNull
    private HikariConfig getHikariConfig() {
        Properties properties = new Properties();

        properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("jdbcUrl", String.format("jdbc:mysql://%s:%s/%s", host, port, database));
        properties.setProperty("dataSource.serverName", host);
        properties.setProperty("dataSource.user", user);
        properties.setProperty("dataSource.password", password);
        properties.setProperty("dataSource.databaseName", database);
        properties.setProperty("dataSource.portNumber", port);

        HikariConfig config = new HikariConfig(properties);
        return config;
    }

    public void disable() {
        this.dataSource.close();
        
        this.plugin.getLogger().info("disconnected hikari pool");
    }
    
    public DataSource getDataSource() {
        return dataSource;
    }

}
