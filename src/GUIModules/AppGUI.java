package GUIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AppGUI extends JFrame {
    private String title_string = "Aplikacja Szkolna";
    private JPanel mainPanel;
    private JPanel barPanel;
    private JPanel buttonPanel;
    private JPanel appPanel;
    private JButton teacherButton;
    private JButton calendarButton;
    private JButton returnButton;

    public AppGUI() {
        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JPanel settingsContent = new JPanel();
        settingsContent.setBackground(new Color(220, 220, 220));
        mainPanel.add(settingsContent, BorderLayout.CENTER);

        barPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        barPanel.add(returnButton, BorderLayout.WEST);
        buttonPanel.add(teacherButton);
        buttonPanel.add(calendarButton);

        mainPanel.add(barPanel, BorderLayout.SOUTH);

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
        for (JButton b : new JButton[]{returnButton}) {
            b.setMaximumSize(buttonSize);
            b.setPreferredSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }
    }
}
