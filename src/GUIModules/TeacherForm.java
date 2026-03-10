package GUIModules;

import DataModules.SettingsManager;
import DataModules.Teacher;
import DataModules.TeacherManager;

import javax.swing.*;
import java.awt.*;

public class TeacherForm extends JDialog {
    private TeacherManager teacherManager;
    private TeachersGUI teachersGUI;
    private SettingsManager settingsManager;

    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField surnameField = new JTextField(15);
    private JTextField fteField = new JTextField(15);
    private JCheckBox availableBox = new JCheckBox("Dostępny");
    private JTextField restrictionsField = new JTextField(15);
    private JSpinner workHoursField = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));


    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    private JButton saveButton = new JButton("Save");
    private JButton cancelButton = new JButton("Cancel");

    public TeacherForm(Frame parent, TeacherManager teacherManager, TeachersGUI teachersGUI, SettingsManager settingsManager) {
        super(parent, "Dodaj nauczyciela", true);

        this.teacherManager = teacherManager;
        this.teachersGUI = teachersGUI;
        this.settingsManager = settingsManager;

        setLayout(new BorderLayout());

        mainPanel = new JPanel(new GridBagLayout());
        buttonPanel = new JPanel(new GridBagLayout());

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        Font formFont = new Font("Arial", Font.BOLD, 20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Imie:");
        JLabel surnameLabel = new JLabel("Nazwisko:");
        JLabel fteLabel = new JLabel("Wymiar etatu:");
        JLabel availableLabel = new JLabel("Dostępność:");
        JLabel restrictionsLabel = new JLabel("Ograniczenia:");
        JLabel workHoursLabel = new JLabel("Godziny pracy:");
        idLabel.setFont(formFont);
        nameLabel.setFont(formFont);
        surnameLabel.setFont(formFont);
        fteLabel.setFont(formFont);
        availableLabel.setFont(formFont);
        restrictionsLabel.setFont(formFont);
        workHoursLabel.setFont(formFont);
        availableBox.setFont(formFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(surnameLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(fteLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(fteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(availableLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(availableBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(restrictionsLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(restrictionsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(workHoursLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(workHoursField, gbc);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridy = 0;
        gbcButtons.weightx = 1;

        gbcButtons.gridx = 0;
        gbcButtons.anchor = GridBagConstraints.WEST;
        buttonPanel.add(cancelButton, gbcButtons);

        gbcButtons.gridx = 1;
        gbcButtons.anchor = GridBagConstraints.EAST;
        buttonPanel.add(saveButton, gbcButtons);

        saveButton.setPreferredSize(new Dimension(200,60));
        cancelButton.setPreferredSize(new Dimension(200,60));

        saveButton.setFocusPainted(false);
        cancelButton.setFocusPainted(false);

        saveButton.addActionListener(e -> {
            int minutesPerWorkHour = settingsManager.getSettings().getDutyMinutesPerWorkHour();
            double workHours = ((Number) workHoursField.getValue()).doubleValue();
            double dutyMinutes = workHours * minutesPerWorkHour;

            Teacher t = new Teacher(
                    idField.getText(),
                    nameField.getText(),
                    surnameField.getText(),
                    fteField.getText(),
                    availableBox.isSelected() ? "Dostępny" : "Niedostępny",
                    restrictionsField.getText(),
                    workHours,
                    dutyMinutes,
                    dutyMinutes
            );
            teacherManager.addTeacher(t);
            teacherManager.saveTeachers();
            teachersGUI.transferTeachers();
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        pack();
        setMinimumSize(new Dimension(600,450));
        setLocationRelativeTo(parent);
    }
}
