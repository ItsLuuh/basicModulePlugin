package net.luuh.test.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.luuh.test.utils.RCUtils;
import org.bukkit.plugin.java.JavaPlugin;

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

        String key = "storage.";
        String address = RCUtils.readString(key + "address");
        if(address.contains(":")) {
            String[] parts = address.split(":");

            if (parts.length >= 2) {
                this.host = parts[0];
                this.port = parts[1];
            } else {
                this.host = address;
                this.port = "3306";
            }
        } else {
            this.host = address;
            this.port = "3306";
        }
        this.database = RCUtils.readString(key + "database");
        this.user = RCUtils.readString(key + "username");
        this.password = RCUtils.readString(key + "password");
    }
    
    public void enable() throws IllegalStateException {
        this.plugin.getLogger().info("connecting hikari...");

        Properties properties = new Properties();

        properties.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("jdbcUrl", String.format("jdbc:mysql://%s:%s/%s", host, port, database));
        properties.setProperty("dataSource.serverName", host);
        properties.setProperty("dataSource.user", user);
        properties.setProperty("dataSource.password", password);
        properties.setProperty("dataSource.databaseName", database);
        properties.setProperty("dataSource.portNumber", port);

        HikariConfig config = new HikariConfig(properties);

        config.setPoolName("#descent-pool");
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("allowMultiQueries", true);
        config.setMaximumPoolSize(5);

        this.dataSource = new HikariDataSource(config);

        this.plugin.getLogger().info("connected hikari pool");
    }
    
    public void disable() {
        this.dataSource.close();
        
        this.plugin.getLogger().info("disconnected hikari pool");
    }
    
    public DataSource getDataSource() {
        return dataSource;
    }

}
