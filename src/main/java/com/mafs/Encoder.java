package com.mafs;

import com.mafs.helper.Mapping;
import org.bukkit.Material;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Encoder {
    final private File file;

    public Encoder(File file) {
        this.file = file;
    }

    public Material[] getBlocks() throws IOException {
        byte[] bytes = new byte[(int)file.length()];

        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(bytes);
        fileInputStream.close();

        Material[] materials = new Material[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            materials[i] = Mapping.byteToBlock(bytes[i]);
        }

        return materials;
    }
}
