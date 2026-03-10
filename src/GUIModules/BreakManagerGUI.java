package GUIModules;

import DataModules.SettingsManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BreakManagerGUI extends JPanel {

    private Frame parent;
    private SettingsManager settingsManager;

    private JPanel mainPanel = new JPanel();
    private JPanel barPanel = new JPanel();
    private JPanel breakPanel = new JPanel();
    private JScrollPane breakScrollPane;

    private JButton returnButton = new JButton("Powrót");

    // Polish day names (Monday → Sunday)
    private static final String[] POLISH_DAYS = {
            "Poniedziałek",
            "Wtorek",
            "Środa",
            "Czwartek",
            "Piątek",
            "Sobota",
            "Niedziela"
    };

    public BreakManagerGUI(Frame parent, SettingsManager settingsManager) {
        this.parent = parent;
        this.settingsManager = settingsManager;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        breakPanel.setLayout(new GridLayout(1, 7, 10, 0)); // 1 row, 7 columns
        breakPanel.setBackground(new Color(224, 224, 224));
        breakPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 5; i++) {
            JPanel dayPanel = new JPanel();
            dayPanel.setBorder(new LineBorder(Color.BLACK, 2));
            dayPanel.setBackground(new Color(240, 240, 240));
            dayPanel.setLayout(new BorderLayout());

            JLabel dayLabel = new JLabel(POLISH_DAYS[i], SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 25));

            dayPanel.add(dayLabel, BorderLayout.NORTH);

            dayPanel.add(new JLabel(" "), BorderLayout.CENTER);

            breakPanel.add(dayPanel);
        }

        breakScrollPane = new JScrollPane(breakPanel);
        breakScrollPane.setBorder(null);
        breakScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        breakScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        breakScrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(breakScrollPane, BorderLayout.CENTER);

        barPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        barPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        barPanel.add(returnButton);

        returnButton.setPreferredSize(new Dimension(200, 60));
        returnButton.setFocusPainted(false);

        mainPanel.add(barPanel, BorderLayout.SOUTH);

        returnButton.addActionListener(e -> parent.showCard("SELECT"));

        setVisible(true);
    }
}