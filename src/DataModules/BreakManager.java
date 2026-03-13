package DataModules;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class BreakManager {
    private static final String BREAKS_FILE = "breaks.dat";
    private static final String PLACES_FILE = "places.dat";

    private List<Break> breakList;
    private List<String> places;

    public BreakManager() {
        this.breakList = new ArrayList<>();
        this.places = new ArrayList<>();
    }

    public List<Break> getBreakList() {
        return breakList;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public void saveBreaks() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(BREAKS_FILE))) {
            oos.writeObject(breakList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBreaks() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(BREAKS_FILE))) {
            breakList = (List<Break>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            breakList = new ArrayList<>();
        }
    }

    public void savePlaces() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(PLACES_FILE))) {
            oos.writeObject(places);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlaces() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(PLACES_FILE))) {
            places = (List<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            places = new ArrayList<>();
        }
    }

    public Break getOrCreateBreak(DayOfWeek dayOfWeek, LocalTime startTime, String place, int durationMinutes) {
        for (Break b : breakList) {
            if (b.getDayOfWeek() == dayOfWeek &&
                    b.getStartTime().equals(startTime) &&
                    b.getPlace().equals(place)) {
                return b;
            }
        }

        Break newBreak = new Break(durationMinutes, dayOfWeek, startTime, place);
        breakList.add(newBreak);
        return newBreak;
    }

    public void addPlace(String place) {
        if (!places.contains(place)) {
            places.add(place);
        }
    }

    public void removePlace(String place) {
        if (places.remove(place)) {
            // Remove all breaks belonging to this place
            breakList.removeIf(b -> place.equals(b.getPlace()));
        }
    }
}
