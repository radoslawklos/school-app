package DataModules;

import java.util.List;

public class AutomaticTeacherAsigner {
    private final BreakManager breakManager;
    private final TeacherManager teacherManager;
    private final int teachersPerBreak;

    public AutomaticTeacherAsigner(BreakManager breakManager, TeacherManager teacherManager, SettingsManager settingsManager) {
        this.breakManager = breakManager;
        this.teacherManager = teacherManager;
        this.teachersPerBreak = Math.max(0, settingsManager.getSettings().getTreachersPerBreak());
        List<Teacher> teachers = teacherManager.getTeachers();
        List<Break> breaks = breakManager.getBreakList();
        for (Break b : breaks) {
            assignTeachersToBreak(teachers, b);
        }
    }


    private void assignTeachersToBreak(List<Teacher> teachers, Break b) {
        List<Teacher> assigned = b.getTeachers();
        if (assigned == null || assigned.size() >= teachersPerBreak) {
            return;
        }
        int needed = teachersPerBreak - assigned.size();
        for (Teacher t : teachers) {
            if (needed <= 0) break;
            if (!"Dostępny".equalsIgnoreCase(t.getAvailable())) continue;
            double remaining = breakManager.getRemainingDutyMinutes(t);
            if (remaining < b.getDuration()) continue;
            boolean alreadyOnThisBreak = assigned.stream().anyMatch(a -> a.getID().equals(t.getID()));
            if (alreadyOnThisBreak) continue;
            b.addTeacher(t);
            needed--;
        }
    }
}
