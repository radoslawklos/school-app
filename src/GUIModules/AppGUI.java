package GUIModules;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import GUIModules.*;

public class AppGUI extends JPanel {

    private Frame parent;

    private String title_string = "Aplikacja Szkolna";
    private JPanel mainPanel =  new JPanel();
    private JPanel barPanel =  new JPanel();
    private JPanel buttonPanel =  new JPanel();
    private JButton teacherButton = new JButton("Nauczyciele");
    private JButton calendarButton =  new JButton("Kalendarz");
    private JButton returnButton = new JButton("Powrót");

    public AppGUI(Frame parent) {

        this.parent = parent;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(barPanel, BorderLayout.SOUTH);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(teacherButton);
        buttonPanel.add(calendarButton);

        barPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        barPanel.add(returnButton, BorderLayout.WEST);
        buttonPanel.add(teacherButton);
        buttonPanel.add(calendarButton);

        mainPanel.add(barPanel, BorderLayout.SOUTH);

        buttonResize();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                buttonResize();
            }
        });

        returnButton.addActionListener(e -> {
            parent.showCard("MENU");
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
        for (JButton b : new JButton[]{returnButton, teacherButton, calendarButton}) {
            b.setMaximumSize(buttonSize);
            b.setPreferredSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }
    }
}
