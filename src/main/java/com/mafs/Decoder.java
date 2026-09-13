package com.mafs;

import com.mafs.helper.Mapping;
import org.bukkit.Material;

import java.util.List;

public class Decoder {
    public static byte[] decode(List<Material> materials) {
        byte[] bytes = new byte[materials.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = Mapping.blockToByte(materials.get(i));
        }

        return bytes;
    }
}
