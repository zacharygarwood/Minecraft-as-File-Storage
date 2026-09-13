package com.mafs.helper;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static com.mafs.helper.Constants.EOF_MARKER;
import static com.mafs.helper.Constants.MATERIALS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MappingTest {

    @Test
    void mapsEveryByteToADistinctBlock() {
        assertEquals(256, MATERIALS.length);
        assertEquals(256, new HashSet<>(Arrays.asList(MATERIALS)).size());
    }

    @Test
    void usesOnlyPlaceableBlocks() {
        for (Material material : MATERIALS) {
            assertTrue(material.isBlock(), material + " is not a block");
            assertFalse(material.isAir(), material + " is air");
            assertFalse(material.hasGravity(), material + " falls when unsupported");
            assertNotEquals(EOF_MARKER, material);
        }
    }

    @Test
    void roundTripsEveryByte() {
        for (int i = 0; i < 256; i++) {
            byte b = (byte) i;
            assertEquals(b, Mapping.blockToByte(Mapping.byteToBlock(b)));
        }
    }

    @Test
    void rejectsUnmappedBlocks() {
        assertThrows(IllegalArgumentException.class, () -> Mapping.blockToByte(Material.AIR));
        assertThrows(IllegalArgumentException.class, () -> Mapping.blockToByte(EOF_MARKER));
    }
}
