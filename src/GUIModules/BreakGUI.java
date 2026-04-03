package GUIModules;

import DataModules.Break;
import DataModules.BreakManager;
import DataModules.ExtraPlace;
import DataModules.Teacher;
import DataModules.TeacherManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class BreakGUI extends JPanel {
    private Break breakModule;
    private TeacherManager teacherManager;
    private BreakManager breakManager;
    private JLabel teacherLabel = new JLabel("", SwingConstants.CENTER);
    private JLabel extraPlaceLabel = new JLabel("", SwingConstants.CENTER);

    public BreakGUI(Break breakModule, TeacherManager teacherManager, BreakManager breakManager) {
        this.breakModule = breakModule;
        this.teacherManager = teacherManager;
        this.breakManager = breakManager;

        setLayout(new BorderLayout());
        applyCellColor();
        this.setBorder(new LineBorder(Color.BLACK, 2));

        teacherLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(teacherLabel, BorderLayout.CENTER);

        extraPlaceLabel.setOpaque(false);
        extraPlaceLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JButton extraPlaceButton = new JButton("Dodatkowe miejsce");
        extraPlaceButton.setFont(new Font("Arial", Font.PLAIN, 11));
        extraPlaceButton.setFocusPainted(false);
        extraPlaceButton.addActionListener(e -> openExtraPlaceDialog());

        JPanel southPanel = new JPanel(new BorderLayout(2, 2));
        southPanel.setOpaque(false);
        southPanel.add(extraPlaceLabel, BorderLayout.CENTER);
        southPanel.add(extraPlaceButton, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);

        updateTeacherLabel();
        updateExtraPlaceLabel();

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                BreakGUI.this.setBackground(getHoverColor());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                applyCellColor();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    openColorPickerDialog();
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    openTeacherAssignDialog();
                }
            }
        });
    }

    private void applyCellColor() {
        setBackground(breakModule.getBackgroundColor());
    }

    private Color getHoverColor() {
        Color current = breakModule.getBackgroundColor();
        return current.darker();
    }

    private void openColorPickerDialog() {
        Color selectedColor = JColorChooser.showDialog(
                this,
                "Wybierz kolor przerwy",
                breakModule.getBackgroundColor()
        );

        if (selectedColor == null) {
            return;
        }

        breakModule.setBackgroundColor(selectedColor);
        applyCellColor();
        breakManager.saveBreaks();
        repaint();
    }

    private void openExtraPlaceDialog() {
        List<Teacher> allTeachers = teacherManager.getTeachers();
        if (allTeachers == null || allTeachers.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Brak nauczycieli na liście.",
                    "Dodatkowe miejsce",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(18);
        JComboBox<String> teacherCombo = new JComboBox<>();
        for (Teacher t : allTeachers) {
            teacherCombo.addItem(t.getName() + " " + t.getSurname());
        }
        teacherCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));

        ExtraPlace current = breakModule.getExtraPlace();
        if (current != null) {
            if (current.getName() != null) {
                nameField.setText(current.getName());
            }
            if (current.getTeacher() != null) {
                for (int i = 0; i < allTeachers.size(); i++) {
                    if (allTeachers.get(i).getID().equals(current.getTeacher().getID())) {
                        teacherCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Nazwa miejsca:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        form.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        form.add(new JLabel("Nauczyciel:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        form.add(teacherCombo, gbc);

        Font oldButtonFont = UIManager.getFont("OptionPane.buttonFont");
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));

        String[] options = {"Anuluj", "Wyczyść", "Zapisz"};
        int result;
        try {
            result = JOptionPane.showOptionDialog(
                    this,
                    form,
                    "Dodatkowe miejsce przy przerwie",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[2]
            );
        } finally {
            UIManager.put("OptionPane.buttonFont", oldButtonFont);
        }

        if (result == 1) {
            breakModule.setExtraPlace(null);
            updateExtraPlaceLabel();
            breakManager.saveBreaks();
            breakManager.updateRemainingDutyMinutesForTeachers(teacherManager);
            return;
        }
        if (result != 2) {
            return;
        }

        String placeName = nameField.getText().trim();
        int idx = teacherCombo.getSelectedIndex();
        if (placeName.isEmpty() || idx < 0) {
            JOptionPane.showMessageDialog(this,
                    "Podaj nazwę miejsca i wybierz nauczyciela.",
                    "Dodatkowe miejsce",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Teacher chosen = allTeachers.get(idx);
        breakModule.setExtraPlace(new ExtraPlace(placeName, chosen));
        updateExtraPlaceLabel();
        breakManager.saveBreaks();
        breakManager.updateRemainingDutyMinutesForTeachers(teacherManager);
    }

    private static String htmlEscape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private void updateExtraPlaceLabel() {
        ExtraPlace ep = breakModule.getExtraPlace();
        if (ep == null || !ep.isConfigured()) {
            extraPlaceLabel.setVisible(false);
            extraPlaceLabel.setText("");
            return;
        }
        extraPlaceLabel.setVisible(true);
        String nameLine = htmlEscape(ep.getName().trim());
        String teacherLine = htmlEscape(ep.getTeacher().getName() + " " + ep.getTeacher().getSurname());
        extraPlaceLabel.setText("<html><div style='color:red;font-size:20pt;font-family:Arial;text-align:center;'>"
                + nameLine + "<br>" + teacherLine + "</div></html>");
    }

    private void openTeacherAssignDialog() {
        List<Teacher> allTeachers = teacherManager.getTeachers();
        List<Teacher> availableTeachers = new ArrayList<>();

        for (Teacher t : allTeachers) {
            if ("Dostępny".equalsIgnoreCase(t.getAvailable()) && breakManager.getRemainingDutyMinutes(t) > 0) {
                availableTeachers.add(t);
            }
        }


        JLabel messageLabel = new JLabel("Wybierz nauczyciela:");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // build list of teachers that are not already assigned (for clarity)
        List<Teacher> currentlyAssigned = breakModule.getTeachers();
        List<Teacher> selectableTeachers = new ArrayList<>();
        for (Teacher t : availableTeachers) {
            boolean alreadyAssigned = false;
            if (currentlyAssigned != null) {
                for (Teacher a : currentlyAssigned) {
                    if (a.getID().equals(t.getID())) {
                        alreadyAssigned = true;
                        break;
                    }
                }
            }
            if (!alreadyAssigned) {
                selectableTeachers.add(t);
            }
        }

        JComboBox<String> comboBox = new JComboBox<>();
        for (Teacher t : selectableTeachers) {
            comboBox.addItem(t.getName() + " " + t.getSurname());
        }
        comboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.add(messageLabel, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);

        Font oldButtonFont = UIManager.getFont("OptionPane.buttonFont");
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));

        String[] options = {"Anuluj", "Wyczyść", "Zapisz"};

        int result;
        try {
            result = JOptionPane.showOptionDialog(
                    this,
                    panel,
                    "Przypisz nauczyciela",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[2]
            );
        } finally {
            UIManager.put("OptionPane.buttonFont", oldButtonFont);
        }

        if (result == 1) { // "Wyczyść"
            breakModule.setTeachers(new ArrayList<>());
            updateTeacherLabel();
            breakManager.saveBreaks();
            breakManager.updateRemainingDutyMinutesForTeachers(teacherManager);
        } else if (result == 2) { // "Zapisz"
            int selectedIndex = comboBox.getSelectedIndex();
            if (selectedIndex < 0) {
                return;
            }

            List<Teacher> assigned = breakModule.getTeachers();
            if (assigned == null) {
                assigned = new ArrayList<>();
                breakModule.setTeachers(assigned);
            }

            if (assigned.size() >= 4) {
                JLabel warnLabel = new JLabel("Możesz przypisać maksymalnie 4 nauczycieli do jednej przerwy.");
                warnLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                JOptionPane.showMessageDialog(
                        this,
                        warnLabel,
                        "Błąd",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Teacher selectedTeacher = selectableTeachers.get(selectedIndex);
            assigned.add(selectedTeacher);
            updateTeacherLabel();
            breakManager.saveBreaks();
            breakManager.updateRemainingDutyMinutesForTeachers(teacherManager);
        }
    }

    private void updateTeacherLabel() {
        List<Teacher> teachers = breakModule.getTeachers();

        if (teachers == null || teachers.isEmpty()) {
            teacherLabel.setText("");
            return;
        }

        StringBuilder sb = new StringBuilder("<html><div style='font-size:20pt;'>"); // <--- tutaj rozmiar czcionki

        for (Teacher t : teachers) {
            sb.append(t.getName())
                    .append(" ")
                    .append(t.getSurname())
                    .append("<br>");
        }

        sb.append("</div></html>");

        teacherLabel.setText(sb.toString());
    }
}