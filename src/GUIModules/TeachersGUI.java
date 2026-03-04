package GUIModules;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TeachersGUI extends JPanel {

    private Frame parent;

    private JPanel mainPanel = new JPanel();
    private JPanel barPanel = new JPanel();
    private JPanel teachersPanel = new JPanel();

    private JButton returnButton = new JButton("Powrót");
    private JButton addButton = new JButton("Dodaj Nauczyciela");

    public  TeachersGUI(Frame parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                MainMenu.buttonResize(barPanel, new JButton[]{returnButton, addButton});
            }
        });

        returnButton.addActionListener(e -> {
            parent.showCard("SELECT");
        });

        addButton.addActionListener(e -> {
            //TODO popraw litener
        });

        setVisible(true);
    }

}
