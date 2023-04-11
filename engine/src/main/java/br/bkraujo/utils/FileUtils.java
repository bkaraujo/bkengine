package br.bkraujo.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static br.bkraujo.engine.Logger.error;

public abstract class FileUtils {
    private FileUtils() {}

    public static boolean createDirectory(Path path) {
        if (Files.exists(path)) return true;

        try {
            Files.createDirectory(path);
        } catch (IOException ex) {
            error("Failed to create %s", path.toString());
            return false;
        }

        return true;
    }

    public static boolean exists(Path path) {
        return Files.exists(path);
    }

    public static boolean delete(Path path) {
        if (Files.notExists(path)) return true;

        try {
            Files.delete(path);
        } catch (IOException e) {
            error("Failed to delete %s", path.toString());
            return false;
        }

        return true;
    }

    public static boolean copyTo(InputStream in, Path target) {
        if (in == null) {
            error(String.format("Resource not found: %s", target.toString()));
            return false;
        }

        final var buffer = new byte[1024];
        try {
            final var out = new FileOutputStream(target.toFile());
            for (int i = in.read(buffer) ; i != -1 ; i = in.read(buffer)) out.write(buffer, 0, i);
            out.close(); in.close();
        } catch (IOException ex) {
            error("Failed to create %s", target.getFileName(), ex.toString());
            return false;
        }

        return true;
    }
}
