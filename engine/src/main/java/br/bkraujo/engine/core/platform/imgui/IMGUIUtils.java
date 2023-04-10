package br.bkraujo.engine.core.platform.imgui;

import br.bkraujo.engine.core.platform.Platform;
import br.bkraujo.utils.FileUtils;
import br.bkraujo.utils.Reflections;

import java.nio.file.Path;

public abstract class IMGUIUtils {
    private IMGUIUtils(){}
    private static final String fileName = "java64" + (Platform.IS_WINDOWS ? ".dll" : Platform.IS_APPLE ? ".dylib" : ".so");
    private static final Path target = Path.of(System.getProperty("java.io.tmpdir"), fileName);

    public static boolean initialize() {
        if (!FileUtils.delete(target)) return false;

        final var loader = Reflections.classLoader();
        final var stream = loader.getResourceAsStream("imgui/" + target.getFileName());
        if (!FileUtils.copyTo(stream, target)) return false;

        System.setProperty("imgui.library.path", target.getParent().toString());

        return true;
    }

    public static void terminate() {
        FileUtils.delete(target);
    }
}
