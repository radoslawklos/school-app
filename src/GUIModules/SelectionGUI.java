package GUIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SelectionGUI extends JPanel {

    private Frame parent;

    private JPanel mainPanel =  new JPanel();
    private JPanel barPanel =  new JPanel();
    private JPanel buttonPanel =  new JPanel();
    private JPanel centerPanel =  new JPanel();

    private JButton teacherButton = new JButton("Nauczyciele");
    private JButton calendarButton =  new JButton("Kalendarz");
    private JButton returnButton = new JButton("Powrót");

    public SelectionGUI(Frame parent) {

        this.parent = parent;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(barPanel, BorderLayout.SOUTH);

        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 20, 10);

        centerPanel.add(buttonPanel, gbc);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(teacherButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(calendarButton);

        barPanel.setLayout(new GridBagLayout());
        barPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbcBar = new GridBagConstraints();
        gbcBar.gridx = 1;
        gbcBar.gridy = 0;
        gbcBar.anchor = GridBagConstraints.SOUTHWEST;
        gbcBar.weightx = 1;
        gbcBar.weighty = 1;

        returnButton.setPreferredSize(new Dimension(200, 60));
        returnButton.setMaximumSize(new Dimension(200, 60));
        returnButton.setFocusPainted(false);

        barPanel.add(returnButton, gbcBar);

        MainMenu.buttonResize(centerPanel, new JButton[]{calendarButton,  teacherButton});

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                MainMenu.buttonResize(centerPanel, new JButton[]{calendarButton,  teacherButton});
            }
        });

        returnButton.addActionListener(e -> {
            parent.showCard("MENU");
        });

        teacherButton.addActionListener(e -> {
            parent.showCard("TEACHERS");
        });

        setVisible(true);
    }
}
