package com.mafs.helper;

import static com.mafs.helper.Constants.CHUNK_LENGTH;

public final class Layout {
    private Layout() {}

    public record Offset(int x, int y, int z) {}

    // Blocks fill a CHUNK_LENGTH x CHUNK_LENGTH layer at a time and stack layers up to height,
    // then continue in a new column shifted CHUNK_LENGTH blocks along z
    public static Offset offset(int index, int height) {
        int layerSize = CHUNK_LENGTH * CHUNK_LENGTH;
        int columnSize = layerSize * height;
        int column = index / columnSize;
        int inColumn = index % columnSize;

        return new Offset(
                inColumn % CHUNK_LENGTH,
                inColumn / layerSize,
                column * CHUNK_LENGTH + (inColumn / CHUNK_LENGTH) % CHUNK_LENGTH);
    }
}
