package com.mafs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
import static com.mafs.helper.Constants.CHUNK_LENGTH;
import static com.mafs.helper.Constants.CHUNK_HEIGHT;

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
                    placeBlocks(materials, player);
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

    private void placeBlocks(Material[] materials, Player player) {
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        int startOffset = 5;
        int startX = (int) playerLocation.getX() + startOffset;
        int startY = (int) playerLocation.getY();
        int startZ = (int) playerLocation.getZ();

        int x = 0, y = 0, z = 0;

        int totalBlocks = CHUNK_HEIGHT * CHUNK_LENGTH * CHUNK_LENGTH;

        for (int index = 0; index < materials.length; index++) {
            int currentBlock = index % totalBlocks;
            y = currentBlock / (CHUNK_LENGTH * CHUNK_LENGTH);
            z = (currentBlock / CHUNK_LENGTH) % CHUNK_LENGTH;
            x = currentBlock % CHUNK_LENGTH;
            setBlock(world, startX + x, startY + y, startZ + z, materials[index]);

            if (currentBlock == totalBlocks - 1) {
                startZ += CHUNK_LENGTH;
            }
        }

        // Using beacon to denote EOF
        setBlock(world, startX + x, startY + y, startZ + z, Material.BEACON);
    }

    private void setBlock(World world, int x, int y, int z, Material material) {
        world.getBlockAt(x, y, z).setType(material);
    }
}

