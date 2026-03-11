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
    private JPanel topPanel = new JPanel();     // NEW
    private JPanel barPanel = new JPanel();
    private JPanel breakPanel = new JPanel();

    private JButton returnButton = new JButton("Powrót");
    private JButton prevButton = new JButton("←");
    private JButton nextButton = new JButton("→");

    private JLabel dayLabel = new JLabel("", SwingConstants.CENTER);

    private CardLayout cardLayout = new CardLayout();
    private int currentDay = 0;

    // Polish day names
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

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        /* ---------- TOP PANEL (DAY + BUTTONS) ---------- */

        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10,10,10,10));
        topPanel.setBackground(new Color(210,210,210));

        dayLabel.setText(POLISH_DAYS[currentDay]);
        dayLabel.setFont(new Font("Arial", Font.BOLD, 42));
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayLabel.setOpaque(true);
        dayLabel.setBackground(new Color(235,235,235));
        dayLabel.setBorder(new LineBorder(Color.BLACK,2));

        prevButton.setPreferredSize(new Dimension(120,80));
        nextButton.setPreferredSize(new Dimension(120,80));

        prevButton.setFont(new Font("Arial", Font.BOLD, 28));
        nextButton.setFont(new Font("Arial", Font.BOLD, 28));

        prevButton.setFocusPainted(false);
        nextButton.setFocusPainted(false);

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(dayLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        /* ---------- BREAK PANEL (CARDS) ---------- */

        breakPanel.setLayout(cardLayout);
        breakPanel.setBorder(new LineBorder(Color.BLACK,2));

        for (String day : POLISH_DAYS) {

            JPanel dayPanel = new JPanel(new BorderLayout());
            dayPanel.setBackground(new Color(240,240,240));

            dayPanel.add(new JLabel("Break settings here", SwingConstants.CENTER),
                    BorderLayout.CENTER);

            breakPanel.add(dayPanel, day);
        }

        mainPanel.add(breakPanel, BorderLayout.CENTER);

        /* ---------- BOTTOM BAR ---------- */

        barPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        barPanel.setBorder(new EmptyBorder(20,0,0,0));

        returnButton.setPreferredSize(new Dimension(200,60));
        returnButton.setFocusPainted(false);

        barPanel.add(returnButton);

        mainPanel.add(barPanel, BorderLayout.SOUTH);

        /* ---------- BUTTON ACTIONS ---------- */

        prevButton.addActionListener(e -> {
            currentDay--;
            if(currentDay < 0) currentDay = POLISH_DAYS.length-1;
            updateDay();
        });

        nextButton.addActionListener(e -> {
            currentDay++;
            if(currentDay >= POLISH_DAYS.length) currentDay = 0;
            updateDay();
        });

        returnButton.addActionListener(e -> parent.showCard("SELECT"));

        setVisible(true);
    }

    private void updateDay() {
        dayLabel.setText(POLISH_DAYS[currentDay]);
        cardLayout.show(breakPanel, POLISH_DAYS[currentDay]);
    }
}