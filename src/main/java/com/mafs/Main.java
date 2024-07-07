package com.mafs;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import static com.mafs.helper.Constants.ENCODE_COMMAND;
import static com.mafs.helper.Constants.DECODE_COMMAND;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[MAFS]: Plugin is enabled!");

        Commands commands = new Commands();
        getCommand(ENCODE_COMMAND).setExecutor(commands);
        getCommand(DECODE_COMMAND).setExecutor(commands);
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[MAFS]: Plugin is disabled!");
    }
}