package DataModules;

import java.io.Serializable;

public class ExtraPlace implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Teacher teacher;

    public ExtraPlace(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * Shown in the UI only when both a non-empty name and a teacher are set.
     */
    public boolean isConfigured() {
        return teacher != null && name != null && !name.trim().isEmpty();
    }
}
