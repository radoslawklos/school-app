package DataModules;

import java.io.*;

public class AppSettings implements Serializable {
    private int dutyMinutesPerWorkHour;

    public int getDutyMinutesPerWorkHour() {
        return dutyMinutesPerWorkHour;
    }

    public void setDutyMinutesPerWorkHour(int dutyMinutesPerWorkHour) {
        this.dutyMinutesPerWorkHour = dutyMinutesPerWorkHour;
    }

    public AppSettings() {
        this.dutyMinutesPerWorkHour = 10;
    }

    public void SaveSettings() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream("settings.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadSettings() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("settings.dat"))) {

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
