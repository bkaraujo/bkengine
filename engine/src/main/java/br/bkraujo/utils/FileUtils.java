package br.bkraujo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static br.bkraujo.engine.Logger.error;

public abstract class FileUtils {
    private FileUtils() {}

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

    public static boolean copyTo(InputStream input, Path target) {
        if (input == null) {
            error(String.format("Resource not found: %s", target.toString()));
            return false;
        }

        try {
            final var temp = File.createTempFile(target.getFileName().toString(), "");
            final var fos = new FileOutputStream(temp);

            final var buffer = new byte[1024];
            for (int i = input.read(buffer) ; i != -1 ; i = input.read(buffer)) fos.write(buffer, 0, i);

            fos.close();
            input.close();
        } catch (Exception ex) {
            error("Failed to System.load(%s): %s", target.getFileName(), ex.toString());
            return false;
        }

        return true;
    }
}
