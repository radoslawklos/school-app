package GUIModules;

import DataModules.SettingsManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SettingsGUI extends JPanel {

    private Frame parent;
    private SettingsManager settingsManager;

    private JPanel mainPanel = new JPanel();
    private JPanel barPanel = new JPanel();
    private JPanel settingsPanel = new JPanel();
    private JPanel settingsContainer = new JPanel();

    private JButton returnButton = new JButton("Powrót");
    private JButton saveButton = new JButton("Zapisz");

    private JSpinner dutyMinutesPerWorkHourField = new JSpinner(new SpinnerNumberModel(10, 0, null, 1));

    public SettingsGUI(Frame parent, SettingsManager settingsManager) {
        this.parent = parent;
        this.settingsManager = settingsManager;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        settingsPanel.setBorder(new LineBorder(new Color(245, 245, 245), 20));
        settingsPanel.setBackground(new Color(224, 224, 224));

        settingsContainer.setLayout(new GridBagLayout());
        GridBagConstraints containerGbc = new GridBagConstraints();
        containerGbc.gridx = 0;
        containerGbc.gridy = 0;
        containerGbc.anchor = GridBagConstraints.NORTH;
        containerGbc.weightx = 1;
        containerGbc.weighty = 0;
        settingsContainer.add(settingsPanel, containerGbc);

        JScrollPane settingsScrollPane = new JScrollPane(settingsContainer);
        settingsScrollPane.setBorder(null);
        settingsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        settingsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(settingsScrollPane, BorderLayout.CENTER);
        mainPanel.add(barPanel, BorderLayout.SOUTH);


        barPanel.setLayout(new GridBagLayout());
        barPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        barPanel.add(returnButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        barPanel.add(saveButton, gbc);

        returnButton.setPreferredSize(new Dimension(200, 60));
        returnButton.setMaximumSize(new Dimension(200, 60));
        returnButton.setFocusPainted(false);

        saveButton.setPreferredSize(new Dimension(200, 60));
        saveButton.setMaximumSize(new Dimension(200, 60));
        saveButton.setFocusPainted(false);

        MainMenu.buttonResize(barPanel, new JButton[]{returnButton, saveButton});

        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setPreferredSize(new Dimension(600, 150));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 0;
        gbc2.anchor = GridBagConstraints.WEST;

        JLabel dutyLabel = new JLabel("Minuty dyżuru na 1 godzinę pracy:");
        dutyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        settingsPanel.add(dutyLabel, gbc2);

        gbc2.gridx = 1;
        gbc2.weightx = 1;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        dutyMinutesPerWorkHourField.setFont(new Font("Arial", Font.PLAIN, 20));
        dutyMinutesPerWorkHourField.setPreferredSize(new Dimension(200, 50));
        settingsPanel.add(dutyMinutesPerWorkHourField, gbc2);

        dutyMinutesPerWorkHourField.setValue(settingsManager.getSettings().getDutyMinutesPerWorkHour());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                MainMenu.buttonResize(barPanel, new JButton[]{returnButton, saveButton});
            }
        });

        returnButton.addActionListener(e -> {
            parent.showCard("MENU");
        });

        saveButton.addActionListener(e -> {
            int minutes = (Integer) dutyMinutesPerWorkHourField.getValue();
            settingsManager.getSettings().setDutyMinutesPerWorkHour(minutes);
            settingsManager.saveSettings();
            parent.showCard("MENU");
        });

        setVisible(true);
    }
}
