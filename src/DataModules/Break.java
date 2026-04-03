package DataModules;
import java.io.Serializable;
import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Break implements Serializable {
    private static final long serialVersionUID = 1L;

    private int duration;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private String place;
    private ExtraPlace extraPlace;
    private List<Teacher> teachers = new ArrayList<>();
    private Integer backgroundColorRGB;

    public Break(int duration, DayOfWeek dayOfWeek, LocalTime startTime, String place) {
        this.duration = duration;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.place = place;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Teacher> getTeachers() {
        if (teachers == null) {
            teachers = new ArrayList<>();
        }
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher) {
        getTeachers().add(teacher);
    }

    public Color getBackgroundColor() {
        if (backgroundColorRGB == null) {
            return new Color(240, 240, 240);
        }
        return new Color(backgroundColorRGB, true);
    }

    public void setBackgroundColor(Color color) {
        if (color == null) {
            backgroundColorRGB = null;
            return;
        }
        backgroundColorRGB = color.getRGB();
    }

    public ExtraPlace getExtraPlace() {
        return extraPlace;
    }

    public void setExtraPlace(ExtraPlace extraPlace) {
        this.extraPlace = extraPlace;
    }
}
