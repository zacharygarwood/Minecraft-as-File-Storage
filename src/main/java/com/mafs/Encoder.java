package com.mafs;

import com.mafs.helper.Mapping;
import org.bukkit.Material;

public class Encoder {
    public static Material[] encode(byte[] bytes) {
        Material[] materials = new Material[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            materials[i] = Mapping.byteToBlock(bytes[i]);
        }

        return materials;
    }
}
