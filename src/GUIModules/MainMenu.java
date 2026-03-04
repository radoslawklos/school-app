package GUIModules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainMenu extends JPanel{

    private Frame parent;

    private String title_string = "Aplikacja Szkolna";
    private JLabel title = new JLabel(title_string);
    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JButton start = new JButton("Uruchom");
    private JButton exit =  new JButton("Wyjdź z aplikacji");
    private JButton settings =  new JButton("Ustawienia");
    private JPanel titlePanel = new JPanel();


    public MainMenu(Frame parent){

        this.parent = parent;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(titlePanel, gbc);
        gbc.gridy++;
        mainPanel.add(buttonPanel, gbc);

        titlePanel.add(title);


        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        addSpacing(buttonPanel, 200);
        buttonPanel.add(start);
        addSpacing(buttonPanel, 20);
        buttonPanel.add(settings);
        addSpacing(buttonPanel, 20);
        buttonPanel.add(exit);

        buttonResize(buttonPanel, new JButton[]{start, settings, exit});
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                buttonResize(buttonPanel, new JButton[]{start, settings, exit});
            }
        });
        setVisible(true);

        exit.addActionListener(e -> System.exit(0));

        settings.addActionListener(e -> {
            parent.showCard("SETTINGS");
        });

        start.addActionListener(e -> {
            parent.showCard("APPGUI");
        });

    }
    public static void buttonResize(JPanel panelForSize, JButton [] buttons){
        int panel_width = panelForSize.getWidth();
        int panel_height = panelForSize.getHeight();

        int buttonWidth = (int) (panel_width * 0.30);
        int buttonHeight = (int) (panel_height * 0.06);

        buttonWidth = Math.max(400, Math.min(buttonWidth, 600));
        buttonHeight = Math.max(100, Math.min(buttonHeight, 200));

        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        for (JButton b : buttons) {
            b.setMaximumSize(buttonSize);
            b.setPreferredSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }
    }

    public void addSpacing(JPanel panel, int spacing){
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
    }

}
