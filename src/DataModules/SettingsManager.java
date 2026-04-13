package DataModules;

import java.io.*;

public class SettingsManager {
    private static final String SETTINGS_FILE = "settings.dat";

    private AppSettings settings = new AppSettings();

    public AppSettings getSettings() {
        return settings;
    }

    public SettingsManager() {
        loadSettings();
    }

    public void loadSettings() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(DataStoragePaths.resolveDataFile(SETTINGS_FILE)))) {
            settings = (AppSettings) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            settings = new AppSettings();
        }
    }

    public void saveSettings() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(DataStoragePaths.resolveDataFile(SETTINGS_FILE)))) {
            oos.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

