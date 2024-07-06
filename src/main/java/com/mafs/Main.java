package com.mafs;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        
        String filepath = "C:\\Users\\garwo\\Documents\\Projects\\minecraft-as-file-storage\\src\\main\\java\\com\\mafs\\test.txt";
        File file = new File(filepath);
        Encoder encoder = new Encoder(file);

        try {
            Material[] materials = encoder.getBlocks();
            for (Material material : materials) {
                getLogger().info(material.toString());
            }
        } catch (IOException e) {
            getLogger().throwing("Main", "onEnable", e);
        }
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}