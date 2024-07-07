package com.mafs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static com.mafs.helper.Constants.ENCODE_COMMAND;
import static com.mafs.helper.Constants.ENCODE_COMMAND_USAGE;
import static com.mafs.helper.Constants.DECODE_COMMAND;
import static com.mafs.helper.Constants.DECODE_COMMAND_USAGE;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) { return true; }
        Player player = (Player) sender;
        boolean result = true;

        result &= handleEncodeCommand(player, cmd, args);
        // TODO: handleDecodeCommand()

        return result;
    }

    private boolean handleEncodeCommand(Player player, Command cmd, String[] args) {
        if (cmd.getName().equalsIgnoreCase(ENCODE_COMMAND)) {
            if (args.length >= 1) {
                String filepath = System.getProperty("user.dir") + File.separator + "files" + File.separator + args[0];

                File file = new File(filepath);
                Encoder encoder = new Encoder(file);

                getServer().getConsoleSender().sendMessage(ChatColor.BLUE + file.getPath());

                try {
                    Material[] materials = encoder.getBlocks();
                    // TODO: place blocks
                    for (Material material : materials) {
                        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[MAFS]: " + material.toString());
                    }
                } catch (IOException e) {
                    getServer().getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
                    return false;
                }
            } else {
                player.sendMessage(ChatColor.RED + ENCODE_COMMAND_USAGE);
            }
        }
        return true;
    }
}
