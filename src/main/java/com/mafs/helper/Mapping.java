package com.mafs.helper;

import org.bukkit.Material;

import static com.mafs.helper.Constants.MATERIALS;

public class Mapping {
    public static Material byteToBlock(byte b) {
        int index = b & 0xff;
        return MATERIALS[index];
    }

    public static byte blockToByte(Material material) {
        for (int i = 0; i < MATERIALS.length; i++) {
            if (MATERIALS[i].equals(material)) {
                return (byte) i;
            }
        }
        return -1;
    }
}
