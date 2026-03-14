package GUIModules;

import DataModules.Break;
import DataModules.BreakManager;
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

    public BreakGUI(Break breakModule, TeacherManager teacherManager, BreakManager breakManager) {
        this.breakModule = breakModule;
        this.teacherManager = teacherManager;
        this.breakManager = breakManager;

        setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240));
        this.setBorder(new LineBorder(Color.BLACK, 2));

        teacherLabel.setFont(new Font("Arial", Font.BOLD, 15));
        add(teacherLabel, BorderLayout.CENTER);
        updateTeacherLabel();

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                BreakGUI.this.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                BreakGUI.this.setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                openTeacherAssignDialog();
            }
        });
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