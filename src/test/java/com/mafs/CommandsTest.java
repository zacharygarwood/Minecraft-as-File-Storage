package com.mafs;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandsTest {
    private final Path directory = Paths.get("files");

    @Test
    void resolvesFilesInsideTheDirectory() {
        Path expected = directory.toAbsolutePath().normalize().resolve("notes").resolve("todo.txt");

        assertEquals(expected, Commands.resolveInDirectory(directory, "notes/todo.txt"));
        assertEquals(expected, Commands.resolveInDirectory(directory, "notes/../notes/todo.txt"));
    }

    @Test
    void rejectsPathsOutsideTheDirectory() {
        assertNull(Commands.resolveInDirectory(directory, "../server.properties"));
        assertNull(Commands.resolveInDirectory(directory, "/etc/passwd"));
        assertNull(Commands.resolveInDirectory(directory, "."));
    }
}
