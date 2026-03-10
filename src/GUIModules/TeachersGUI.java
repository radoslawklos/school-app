package GUIModules;

import DataModules.SettingsManager;
import DataModules.Teacher;
import DataModules.TeacherManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class TeachersGUI extends JPanel {

    private Frame parent;

    private JPanel mainPanel = new JPanel();
    private JPanel barPanel = new JPanel();
    private JPanel teachersPanel = new JPanel();

    private JButton returnButton = new JButton("Powrót");
    private JButton addButton = new JButton("Dodaj Nauczyciela");

    private JTable teachersTable = new JTable();
    private DefaultTableModel tableModel;

    private TeacherManager teacherManager = new TeacherManager();
    private SettingsManager settingsManager;

    public  TeachersGUI(Frame parent, SettingsManager settingsManager) {
        this.parent = parent;
        this.settingsManager = settingsManager;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        teachersPanel.setLayout(new BorderLayout());
        teachersPanel.setBorder(new LineBorder(new Color(245, 245, 245), 20));
        teachersPanel.setBackground(new Color(224, 224, 224));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(teachersPanel, BorderLayout.CENTER);
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
        barPanel.add(addButton, gbc);

        returnButton.setPreferredSize(new Dimension(200, 60));
        returnButton.setMaximumSize(new Dimension(200, 60));
        returnButton.setFocusPainted(false);

        addButton.setPreferredSize(new Dimension(200, 60));
        addButton.setMaximumSize(new Dimension(200, 60));
        addButton.setFocusPainted(false);

        MainMenu.buttonResize(barPanel, new JButton[]{returnButton, addButton});

        JScrollPane scrollPane = new JScrollPane(teachersTable);
        String[] columnNames = {"ID", "Imię", "Nazwisko", "Wymiar etatu", "Dostępność", "Ograniczenia", "Godziny Pracy", "Minuty dyżurów", "Pozostałe minuty dyżurów"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        teachersTable.setModel(tableModel);
        teachersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        teachersTable.setFont(new Font("Arial", Font.PLAIN, 16));
        teachersTable.setRowHeight(24);
        teachersTable.setShowGrid(true);
        teachersTable.setGridColor(Color.BLACK);

        changeHeader(teachersTable);
        teachersTable.getTableHeader().setReorderingAllowed(false);

        teachersPanel.add(new JScrollPane(teachersTable), BorderLayout.CENTER);

        transferTeachers();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                MainMenu.buttonResize(barPanel, new JButton[]{returnButton, addButton});
            }
        });

        returnButton.addActionListener(e -> {
            updateTeachersFromTable();
            teacherManager.saveTeachers();
            parent.showCard("SELECT");
        });

        addButton.addActionListener(e -> {
            TeacherForm form = new TeacherForm(this.parent, this.teacherManager, this, this.settingsManager);
            form.setVisible(true);
        });

        teachersTable.getModel().addTableModelListener(e -> {
            updateTeachersFromTable();
            teacherManager.saveTeachers();
        });

        setVisible(true);
    }

    public void transferTeachers() {
        tableModel.setRowCount(0);
        teacherManager.loadTeachers();
        List<Teacher> teachers = teacherManager.getTeachers();

        for (Teacher teacher : teachers) {
            tableModel.addRow(new Object[]{
                    teacher.getID(),
                    teacher.getName(),
                    teacher.getSurname(),
                    teacher.getFTE(),
                    teacher.getAvailable(),
                    teacher.getRestrictions(),
                    teacher.getWorkHours(),
                    teacher.getDutyMinutes(),
                    teacher.getRemainingDutyMinutes(),
            });
        }
    }

    public void changeHeader(JTable teachersTable) {
        JTableHeader header = teachersTable.getTableHeader();

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 16));
        headerRenderer.setBorder(new LineBorder(Color.BLACK));
        headerRenderer.setOpaque(true);
        headerRenderer.setBackground(new Color(230, 230, 230));

        for (int i = 0; i < teachersTable.getColumnCount(); i++) {
            teachersTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        header.repaint();
    }

    public void updateTeachersFromTable() {
        for (int i = 0; i < teachersTable.getRowCount(); i++) {
            Teacher t = teacherManager.getTeachers().get(i);
            t.setID((String) teachersTable.getValueAt(i, 0));
            t.setName((String) teachersTable.getValueAt(i, 1));
            t.setSurname((String) teachersTable.getValueAt(i, 2));
            t.setFTE((String) teachersTable.getValueAt(i, 3));
            t.setAvailable((String) teachersTable.getValueAt(i, 4));
            t.setRestrictions((String)  teachersTable.getValueAt(i, 5));
            t.setWorkHours((Double) teachersTable.getValueAt(i, 6));
            t.setDutyMinutes((Double) teachersTable.getValueAt(i, 7));
            t.setRemainingDutyMinutes((Double) teachersTable.getValueAt(i, 8));
        }
    }
}
