package com.mafs.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LayoutTest {

    @Test
    void fillsALayerRowByRow() {
        assertEquals(new Layout.Offset(0, 0, 0), Layout.offset(0, 256));
        assertEquals(new Layout.Offset(15, 0, 0), Layout.offset(15, 256));
        assertEquals(new Layout.Offset(0, 0, 1), Layout.offset(16, 256));
        assertEquals(new Layout.Offset(15, 0, 15), Layout.offset(255, 256));
    }

    @Test
    void stacksLayers() {
        assertEquals(new Layout.Offset(0, 1, 0), Layout.offset(256, 256));
        assertEquals(new Layout.Offset(15, 255, 15), Layout.offset(256 * 256 - 1, 256));
    }

    @Test
    void startsANewColumnWhenHeightIsReached() {
        assertEquals(new Layout.Offset(0, 0, 16), Layout.offset(256 * 256, 256));
        assertEquals(new Layout.Offset(0, 0, 16), Layout.offset(2 * 256, 2));
        assertEquals(new Layout.Offset(1, 1, 33), Layout.offset(4 * 256 + 256 + 16 + 1, 2));
    }
}
