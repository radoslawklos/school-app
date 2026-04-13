package DataModules;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherManager {
    private static final String TEACHERS_FILE = "teachers.dat";

    private List<Teacher> teachers = new ArrayList<>();

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void saveTeachers() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(DataStoragePaths.resolveDataFile(TEACHERS_FILE)))) {
            oos.writeObject(teachers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTeachers() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(DataStoragePaths.resolveDataFile(TEACHERS_FILE)))) {
            teachers = (List<Teacher>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            teachers = new ArrayList<>();
        }
    }
}