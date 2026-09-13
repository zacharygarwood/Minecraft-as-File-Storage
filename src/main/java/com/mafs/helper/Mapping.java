package com.mafs.helper;

import org.bukkit.Material;

import java.util.EnumMap;
import java.util.Map;

import static com.mafs.helper.Constants.MATERIALS;

public class Mapping {
    private static final Map<Material, Byte> BYTES = new EnumMap<>(Material.class);

    static {
        for (int i = 0; i < MATERIALS.length; i++) {
            BYTES.put(MATERIALS[i], (byte) i);
        }
    }

    public static Material byteToBlock(byte b) {
        return MATERIALS[b & 0xff];
    }

    public static byte blockToByte(Material material) {
        Byte b = BYTES.get(material);
        if (b == null) {
            throw new IllegalArgumentException("No byte is mapped to " + material);
        }
        return b;
    }
}
