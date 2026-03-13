package DataModules;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class Break implements Serializable {
    private static final long serialVersionUID = 1L;

    private int duration;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private String place;
    private List<Teacher> teachers;

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
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
