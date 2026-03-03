package GUIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import GUIModules.*;
import javax.swing.border.EmptyBorder;

public class Settings extends JFrame {
    private String title_string = "Aplikacja Szkolna";
    private JPanel mainPanel = new JPanel();
    private JPanel barPanel = new JPanel();
    private JPanel settingsPanel = new JPanel();
    private JButton returnButton = new JButton("Powrót");
    private JButton saveButton = new JButton("Zapisz");

    public Settings() {
        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setSize(800, 600);

        this.add(mainPanel);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        mainPanel.add(barPanel, BorderLayout.SOUTH);


        barPanel.setLayout(new BorderLayout());
        barPanel.add(returnButton, BorderLayout.WEST);
        barPanel.add(saveButton, BorderLayout.EAST);

        buttonResize();

        returnButton.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        saveButton.addActionListener(e -> {
            System.out.println("Ustawienia zapisane!");
        });

        setVisible(true);
    }
    public void buttonResize(){
        int panel_width = barPanel.getWidth();
        int panel_height = barPanel.getHeight();

        int buttonWidth = (int) (panel_width * 0.30);
        int buttonHeight = (int) (panel_height * 0.06);

        buttonWidth = Math.max(200, Math.min(buttonWidth, 300));
        buttonHeight = Math.max(50, Math.min(buttonHeight, 100));

        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        for (JButton b : new JButton[]{returnButton, saveButton}) {
            b.setMaximumSize(buttonSize);
            b.setPreferredSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }
    }
}
