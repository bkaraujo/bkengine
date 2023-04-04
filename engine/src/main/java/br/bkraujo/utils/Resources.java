package br.bkraujo.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import static br.bkraujo.engine.Logger.error;

public abstract class Resources {
    private Resources(){}

    /**
     * Load a file from the JAR resource folder as a string.
     *
     * @param name File name and path
     */
    public static String asString(String name) {
        try {
            final var cl = Thread.currentThread().getContextClassLoader();
            final var resource = cl.getResource(name);
            if (resource == null)  {
                error("Resource [%s] not found", name);
                return null;
            }

            final var path = Paths.get(resource.toURI());
            return Files.readString(path);
        } catch (Exception ex) {
            return null;
        }
    }
}
