package GUIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class Settings extends JFrame {
    private String title_string = "Aplikacja Szkolna";
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel settingsPanel;
    private JButton returnButton;
    private JButton saveButton;

    public Settings() {
        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        JPanel settingsContent = new JPanel();
        settingsContent.setBackground(new Color(220, 220, 220)); // przykładowe tło
        mainPanel.add(settingsContent, BorderLayout.CENTER);

        buttonPanel = new JPanel(new BorderLayout());
        returnButton = new JButton("Powrót do menu");
        saveButton = new JButton("Zapisz");

        buttonPanel.add(returnButton, BorderLayout.WEST);
        buttonPanel.add(saveButton, BorderLayout.EAST);


        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                buttonResize();
            }
        });

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
        int panel_width = buttonPanel.getWidth();
        int panel_height = buttonPanel.getHeight();

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
