package br.bkraujo.utils;

import static br.bkraujo.engine.Logger.error;

public abstract class Reflections {
    private Reflections(){}

    public static <T> boolean set(Class<T> klass, String name, Object value) {
        return set(klass, null, name, value);
    }

    public static <T> boolean set(Class<T> klass, T object, String name, Object value) {
        try {
            final var field = klass.getDeclaredField(name);
            if (!field.trySetAccessible()) return false;
            field.set(object, value);
        } catch (Exception e) {
            error("Failed to set [%s::%s]: %s", klass.getSimpleName(), name, e.toString());
            return false;
        }

        return true;
    }

    public static <T> T instantiate(Class<T> klass) {
        try {
            return klass.getConstructor().newInstance();
        } catch (Exception e) {
            error("Failed to instantiate [%s]: %s", klass.getCanonicalName(), e.toString());
            return null;
        }
    }

    public static ClassLoader classLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
