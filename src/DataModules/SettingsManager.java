package DataModules;

import java.io.*;

public class SettingsManager {

    private AppSettings settings = new AppSettings();

    public AppSettings getSettings() {
        return settings;
    }

    public void loadSettings() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("settings.dat"))) {
            settings = (AppSettings) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            settings = new AppSettings();
        }
    }

    public void saveSettings() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("settings.dat"))) {
            oos.writeObject(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

