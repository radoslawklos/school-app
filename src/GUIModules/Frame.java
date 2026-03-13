package GUIModules;

import DataModules.SettingsManager;
import DataModules.BreakManager;
import DataModules.TeacherManager;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private String title_string = "Aplikacja Szkolna";
    private CardLayout layout = new CardLayout();
    private JPanel container = new JPanel(layout);

    private SettingsManager settingsManager = new SettingsManager();
    private BreakManager breakManager = new BreakManager();
    private TeacherManager teacherManager = new TeacherManager();

    public Frame() {

        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);

        settingsManager.loadSettings();
        breakManager.loadBreaks();
        breakManager.loadPlaces();
        teacherManager.loadTeachers();
        if (breakManager.getPlaces().isEmpty()) {
            breakManager.addPlace("Domyślne miejsce");
            breakManager.savePlaces();
        }

        SettingsGUI settingsGUI = new SettingsGUI(this, settingsManager);
        MainMenu mainMenu = new MainMenu(this, settingsGUI, settingsManager);

        container.add(mainMenu, "MENU");
        container.add(settingsGUI, "SETTINGS");
        container.add(new SelectionGUI(this), "SELECT");
        container.add(new TeachersGUI(this, settingsManager, teacherManager), "TEACHERS");
        container.add(new BreakManagerGUI(this, settingsManager, breakManager, teacherManager), "CALENDAR");

        add(container);
        setVisible(true);
    }

    public void showCard(String name) {
        layout.show(container, name);
    }
}