package GUIModules;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private String title_string = "Aplikacja Szkolna";
    private CardLayout layout = new CardLayout();
    private JPanel container = new JPanel(layout);

    public Frame() {
        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);

        container.add(new MainMenu(this), "MENU");
        container.add(new Settings(this), "SETTINGS");
        container.add(new AppGUI(this), "APPGUI");
        container.add(new TeachersGUI(this), "TEACHERS");

        add(container);
        setVisible(true);
    }

    public void showCard(String name) {
        layout.show(container, name);
    }

}
