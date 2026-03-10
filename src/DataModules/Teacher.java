package DataModules;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Teacher implements Serializable {

    public String ID;
    public String name;
    public String surname;
    public String FTE;
    public String available;
    public String restrictions;
    public double workHours;
    public double dutyMinutes;
    public double remainingDutyMinutes;

    public Teacher(String ID, String name, String surname, String FTE, String available, String restrictions, double workHours, double dutyMinutes,  double remainingDutyMinutes) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.FTE = FTE;
        this.available = available;
        this.restrictions = restrictions;
        this.workHours = workHours;
        this.dutyMinutes = dutyMinutes;
        this.remainingDutyMinutes = remainingDutyMinutes;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFTE() {
        return FTE;
    }

    public void setFTE(String FTE) {
        this.FTE = FTE;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getDutyMinutes() {
        return dutyMinutes;
    }

    public void setDutyMinutes(double dutyMinutes) {
        this.dutyMinutes = dutyMinutes;
    }

    public double getRemainingDutyMinutes() {
        return remainingDutyMinutes;
    }

    public void setRemainingDutyMinutes(double remainingDutyMinutes) {
        this.remainingDutyMinutes = remainingDutyMinutes;
    }

    @Override
    public String toString() {
        String teacherString = "";
        teacherString = teacherString.concat("\n").concat("ID: " + ID + "\n");
        teacherString = teacherString.concat("\n").concat("Name: " + name + "\n");
        teacherString = teacherString.concat("\n").concat("Surname: " + surname + "\n");
        teacherString = teacherString.concat("\n").concat("FTE: " + FTE + "\n");
        teacherString = teacherString.concat("\n").concat("Available: " + available + "\n");
        teacherString = teacherString.concat("\n").concat("Work Hours: " + workHours + "\n");
        teacherString = teacherString.concat("\n").concat("Duty Minutes: " + dutyMinutes + "\n");
        teacherString = teacherString.concat("\n").concat("Remaining duty Minutes: " + remainingDutyMinutes + "\n");
        return teacherString;
    }
}
