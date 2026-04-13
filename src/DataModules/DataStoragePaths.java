package DataModules;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class DataStoragePaths {

    private static final String APP_DIR_NAME = "Aplikacja_szkolna";
    private static final Path APP_DATA_DIR = resolveAppDataDir();

    private DataStoragePaths() {
    }

    public static File resolveDataFile(String fileName) {
        try {
            Files.createDirectories(APP_DATA_DIR);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create app data directory: " + APP_DATA_DIR, e);
        }

        Path target = APP_DATA_DIR.resolve(fileName);
        migrateFromWorkingDirectoryIfNeeded(fileName, target);
        return target.toFile();
    }

    private static Path resolveAppDataDir() {
        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData != null && !localAppData.isBlank()) {
            return Path.of(localAppData, APP_DIR_NAME);
        }
        return Path.of(System.getProperty("user.home"), "." + APP_DIR_NAME);
    }

    private static void migrateFromWorkingDirectoryIfNeeded(String fileName, Path target) {
        if (Files.exists(target)) {
            return;
        }
        Path legacy = Path.of(fileName);
        if (!Files.exists(legacy)) {
            return;
        }
        try {
            Files.copy(legacy, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {
            // Non-fatal: app can still continue with defaults if migration fails.
        }
    }
}
