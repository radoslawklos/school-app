package DataModules;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    public List<Teacher> teachers = new ArrayList<>();

    public String ID;
    public String name;
    public String surname;
    public Double FTE;
    public boolean available;
    public List<String> restrictions;

    public Teacher(String ID, String name, String surname, Double FTE, boolean available, List<String> restrictions) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.FTE = FTE;
        this.available = available;
        this.restrictions = restrictions;

        teachers.add(this);
    }
}
