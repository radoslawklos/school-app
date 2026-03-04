package DataModules;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Teacher implements Serializable {

    public String ID;
    public String name;
    public String surname;
    public Double FTE;
    public boolean available;
    public List<String> restrictions;

    public Teacher(String ID, String name, String surname, Double FTE, boolean available, List<String> restrictions, TeacherManager teacherManager) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.FTE = FTE;
        this.available = available;
        this.restrictions = restrictions;
        teacherManager.addTeacher(this);
    }


    @Override
    public String toString() {
        String teacherString = "";
        teacherString = teacherString.concat("\n").concat("ID: " + ID + "\n");
        teacherString = teacherString.concat("\n").concat("Name: " + name + "\n");
        teacherString = teacherString.concat("\n").concat("Surname: " + surname + "\n");
        teacherString = teacherString.concat("\n").concat("FTE: " + FTE + "\n");
        return teacherString;
    }
}
