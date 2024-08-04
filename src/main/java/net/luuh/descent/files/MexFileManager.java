package net.luuh.descent.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class MexFileManager {

    FileConfiguration dataconfig;
    File dfile;

    public void setup(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        dfile = new File(plugin.getDataFolder(), "messages.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        dataconfig = YamlConfiguration.loadConfiguration(dfile);
    }

    public FileConfiguration getMessages() {
        return dataconfig;
    }

    public void saveData() {
        try {
            dataconfig.save(dfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reloadData() {
        dataconfig = YamlConfiguration.loadConfiguration(dfile);
    }



}
