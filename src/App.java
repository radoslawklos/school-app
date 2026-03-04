import DataModules.Teacher;
import DataModules.TeacherManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        //GUIHandler guiHandler = new GUIHandler();
        TeacherManager tm1 = new TeacherManager();
//        List<String> restrictions1 = Arrays.asList("Monday morning", "Friday afternoon");
//        List<String> restrictions2 = Arrays.asList("Wednesday", "Thursday morning");
//        List<String> restrictions3 = Arrays.asList("Tuesday afternoon");
//        List<String> restrictions4 = Arrays.asList();
//
//        Teacher t1 = new Teacher("T001", "John", "Doe", 1.0, true, restrictions1, tm1);
//        Teacher t2 = new Teacher("T002", "Alice", "Smith", 0.5, true, restrictions2, tm1);
//        Teacher t3 = new Teacher("T003", "Bob", "Johnson", 0.75, false, restrictions3, tm1);
//        Teacher t4 = new Teacher("T004", "Clara", "Williams", 1.0, true, restrictions4, tm1);
//        Teacher t5 = new Teacher("T005", "David", "Brown", 0.6, true, Arrays.asList("Monday", "Wednesday"), tm1);
//
//        tm1.saveTeachers();
        TeacherManager tm2 = new TeacherManager();
        tm2.loadTeachers();
        System.out.println(tm2.getTeachers());
    }
}