package GUIModules;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame{
    private JLabel title;
    private JPanel jPanel;
    private JPanel jButtonPanel;
    private JButton start;
    private JButton exit;
    private JButton settings;
    private String title_string = "Aplikacja Szkolna";

    public MainMenu(){
        setTitle(title_string);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(jPanel);
        setVisible(true);
        jButtonPanel.setLayout(new BoxLayout(jButtonPanel, BoxLayout.Y_AXIS));
        buttonResize();
    }
    public void buttonResize(){
        Dimension buttonSize = new Dimension(200, 40);
        for (JButton b : new JButton[]{start, settings, exit}) {
            b.setMaximumSize(buttonSize);
            b.setPreferredSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }

}
