package GUIModules;

import DataModules.SettingsManager;
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private String title_string = "Aplikacja Szkolna";
    private CardLayout layout = new CardLayout();
    private JPanel container = new JPanel(layout);

    private SettingsManager settingsManager = new SettingsManager();

    public Frame() {
        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);

        settingsManager.loadSettings();

        container.add(new MainMenu(this), "MENU");
        container.add(new SettingsGUI(this, settingsManager), "SETTINGS");
        container.add(new SelectionGUI(this), "SELECT");
        container.add(new TeachersGUI(this, settingsManager), "TEACHERS");

        add(container);
        setVisible(true);
    }

    public void showCard(String name) {
        layout.show(container, name);
    }

}
