package GUIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainMenu extends JFrame{
    private JLabel title;
    private JPanel jPanel;
    private JPanel buttonPanel;
    private JButton start;
    private JButton exit;
    private JButton settings;
    private JPanel titlePAnel;
    private String title_string = "Aplikacja Szkolna";
    private JSeparator separator1;
    private JSeparator separator2;
    private JSeparator separator3;
    private Color background_color = new Color(51,138,52);

    public MainMenu(){
        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(jPanel);

        separator1.setMaximumSize(new Dimension(100,100));
        separator2.setMaximumSize(new Dimension(100,100));
        separator3.setMaximumSize(new Dimension(100,100));

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonResize();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                buttonResize();
            }
        });
        setVisible(true);

        exit.addActionListener(e -> System.exit(0));

        settings.addActionListener(e -> {
            this.dispose();
            new Settings();
        });

        start.addActionListener(e -> {
            this.dispose();
            new AppGUI();
        });

    }
    public void buttonResize(){
        int panel_width = buttonPanel.getWidth();
        int panel_height = buttonPanel.getHeight();

        int buttonWidth = (int) (panel_width * 0.30);
        int buttonHeight = (int) (panel_height * 0.06);

        buttonWidth = Math.max(400, Math.min(buttonWidth, 600));
        buttonHeight = Math.max(100, Math.min(buttonHeight, 200));

        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        for (JButton b : new JButton[]{start, settings, exit}) {
            b.setMaximumSize(buttonSize);
            b.setPreferredSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }
    }

}
