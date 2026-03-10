package DataModules;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class Break {
    int duration;
    DayOfWeek dayOfWeek;
    LocalTime startTime;

    public Break(int duration, DayOfWeek dayOfWeek, LocalTime startTime) {
        this.duration = duration;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

}
