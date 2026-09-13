package com.mafs;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EncoderDecoderTest {

    @Test
    void roundTripsBytes() {
        byte[] bytes = new byte[4096];
        new Random(42).nextBytes(bytes);

        Material[] materials = Encoder.encode(bytes);

        assertEquals(bytes.length, materials.length);
        assertArrayEquals(bytes, Decoder.decode(List.of(materials)));
    }

    @Test
    void roundTripsAnEmptyFile() {
        assertArrayEquals(new byte[0], Decoder.decode(List.of(Encoder.encode(new byte[0]))));
    }

    @Test
    void rejectsBlocksThatAreNotPartOfAFile() {
        assertThrows(IllegalArgumentException.class, () -> Decoder.decode(List.of(Material.AIR)));
    }
}
