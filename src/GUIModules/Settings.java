package GUIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import GUIModules.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Settings extends JPanel {

    private Frame parent;

    private String title_string = "Aplikacja Szkolna";
    private JPanel mainPanel = new JPanel();
    private JPanel barPanel = new JPanel();
    private JPanel settingsPanel = new JPanel();
    private JButton returnButton = new JButton("Powrót");
    private JButton saveButton = new JButton("Zapisz");

    public Settings(Frame parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        settingsPanel.setBorder(new LineBorder(new Color(245, 245, 245), 20));
        settingsPanel.setBackground(new Color(224, 224, 224));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        mainPanel.add(barPanel, BorderLayout.SOUTH);


        barPanel.setLayout(new BorderLayout());
        barPanel.add(returnButton, BorderLayout.WEST);
        barPanel.add(saveButton, BorderLayout.EAST);

        buttonResize();

        returnButton.addActionListener(e -> {
            parent.showCard("MENU");
        });

        saveButton.addActionListener(e -> {
            //TODO popraw litener
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
