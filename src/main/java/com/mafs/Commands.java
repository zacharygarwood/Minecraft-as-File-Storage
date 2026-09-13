package com.mafs;

import com.mafs.helper.Layout;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.mafs.helper.Constants.ENCODE_COMMAND;
import static com.mafs.helper.Constants.ENCODE_COMMAND_USAGE;
import static com.mafs.helper.Constants.CHUNK_HEIGHT;
import static com.mafs.helper.Constants.START_OFFSET;
import static com.mafs.helper.Constants.FILES_DIRECTORY;
import static com.mafs.helper.Constants.EOF_MARKER;


public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) { return true; }

        if (cmd.getName().equalsIgnoreCase(ENCODE_COMMAND)) {
            handleEncodeCommand(player, args);
        }
        // TODO: handleDecodeCommand()

        return true;
    }

    private void handleEncodeCommand(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + ENCODE_COMMAND_USAGE);
            return;
        }

        Path path = resolveInDirectory(filesDirectory(), args[0]);
        if (path == null || !Files.isRegularFile(path)) {
            player.sendMessage(ChatColor.RED + "File not found in the " + FILES_DIRECTORY + " directory: " + args[0]);
            return;
        }

        Location origin = structureOrigin(player);
        int height = structureHeight(origin);
        if (height <= 0) {
            player.sendMessage(ChatColor.RED + "There is no room to build the structure here");
            return;
        }

        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + "Could not read " + args[0] + ": " + e.getMessage());
            return;
        }

        Material[] materials = Encoder.encode(bytes);
        for (int i = 0; i < materials.length; i++) {
            blockAt(origin, height, i).setType(materials[i]);
        }
        blockAt(origin, height, materials.length).setType(EOF_MARKER);

        player.sendMessage(ChatColor.GREEN + "Encoded " + args[0] + " (" + bytes.length + " bytes). Stand at "
                + coordinates(player.getLocation()) + " to decode it.");
    }

    // Returns null for paths that escape the directory, e.g. "../server.properties"
    static Path resolveInDirectory(Path directory, String name) {
        Path dir = directory.toAbsolutePath().normalize();
        Path path = dir.resolve(name).normalize();
        return path.startsWith(dir) && !path.equals(dir) ? path : null;
    }

    private Path filesDirectory() {
        return Paths.get(System.getProperty("user.dir"), FILES_DIRECTORY);
    }

    private Location structureOrigin(Player player) {
        return player.getLocation().getBlock().getLocation().add(START_OFFSET, 0, 0);
    }

    // Keeps the structure below the world's build limit so no blocks are lost
    private int structureHeight(Location origin) {
        return Math.min(CHUNK_HEIGHT, origin.getWorld().getMaxHeight() - origin.getBlockY());
    }

    private Block blockAt(Location origin, int height, int index) {
        Layout.Offset offset = Layout.offset(index, height);
        return origin.getWorld().getBlockAt(
                origin.getBlockX() + offset.x(),
                origin.getBlockY() + offset.y(),
                origin.getBlockZ() + offset.z());
    }

    private String coordinates(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }
}
