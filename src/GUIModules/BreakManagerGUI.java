package GUIModules;

import DataModules.Break;
import DataModules.BreakManager;
import DataModules.TeacherManager;
import DataModules.Teacher;
import DataModules.SettingsManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BreakManagerGUI extends JPanel {

    private Frame parent;
    private SettingsManager settingsManager;
    private BreakManager breakManager;
    private TeacherManager teacherManager;

    private JPanel mainPanel = new JPanel();
    private JPanel topPanel = new JPanel();     // NEW
    private JPanel barPanel = new JPanel();
    private JPanel breakPanel = new JPanel();

    private JButton returnButton = new JButton("Powrót");
    private JButton prevButton = new JButton("←");
    private JButton nextButton = new JButton("→");
    private JButton addPlaceButton = new JButton("Dodaj miejsce");
    private JButton deletePlaceButton = new JButton("Usuń miejsce");
    private JButton saveToPDFButton = new JButton("Zapisz do pdf");

    private JLabel dayLabel = new JLabel("", SwingConstants.CENTER);

    private CardLayout cardLayout = new CardLayout();
    private int currentDay = 0;

    private static final String[] BREAK_LABELS = {
            "7:50–8:00",
            "8:45–8:55",
            "9:40–9:50",
            "10:35–10:45",
            "11:30–11:50",
            "12:35–12:55",
            "13:40–13:50",
            "14:35–14:45",
            "15:30–15:35"
    };

    private static final LocalTime[] BREAK_STARTS = {
            LocalTime.of(7, 50),
            LocalTime.of(8, 45),
            LocalTime.of(9, 40),
            LocalTime.of(10, 35),
            LocalTime.of(11, 30),
            LocalTime.of(12, 35),
            LocalTime.of(13, 40),
            LocalTime.of(14, 35),
            LocalTime.of(15, 30)
    };

    private static final int[] BREAK_DURATIONS_MIN = {
            10, // 7:50–8:00
            10, // 8:45–8:55
            10, // 9:40–9:50
            10, // 10:35–10:45
            20, // 11:30–11:50
            20, // 12:35–12:55
            10, // 13:40–13:50
            10, // 14:35–14:45
            5   // 15:30–15:35
    };

    // Polish day names
    private static final String[] POLISH_DAYS = {
            "Poniedziałek",
            "Wtorek",
            "Środa",
            "Czwartek",
            "Piątek",
            "Sobota",
            "Niedziela"
    };

    public BreakManagerGUI(Frame parent, SettingsManager settingsManager, BreakManager breakManager, TeacherManager teacherManager) {
        this.parent = parent;
        this.settingsManager = settingsManager;
        this.breakManager = breakManager;
        this.teacherManager = teacherManager;

        this.breakManager.loadPlaces();
        this.breakManager.loadBreaks();


        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        /* ---------- TOP PANEL (DAY + BUTTONS) ---------- */

        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10,10,10,10));
        topPanel.setBackground(new Color(210,210,210));

        dayLabel.setText(POLISH_DAYS[currentDay]);
        dayLabel.setFont(new Font("Arial", Font.BOLD, 42));
        dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dayLabel.setOpaque(true);
        dayLabel.setBackground(new Color(235,235,235));
        dayLabel.setBorder(new LineBorder(Color.BLACK,2));

        prevButton.setPreferredSize(new Dimension(120,80));
        nextButton.setPreferredSize(new Dimension(120,80));

        prevButton.setFont(new Font("Arial", Font.BOLD, 28));
        nextButton.setFont(new Font("Arial", Font.BOLD, 28));

        prevButton.setFocusPainted(false);
        nextButton.setFocusPainted(false);

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(dayLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        /* ---------- BREAK PANEL (CARDS) ---------- */

        breakPanel.setLayout(cardLayout);
        breakPanel.setBorder(new LineBorder(Color.BLACK,2));

        buildDayPanels();

        mainPanel.add(breakPanel, BorderLayout.CENTER);

        /* ---------- BOTTOM BAR ---------- */

        barPanel.setLayout(new GridBagLayout());
        barPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbcBar = new GridBagConstraints();
        gbcBar.gridx = 0;
        gbcBar.gridy = 0;
        gbcBar.weightx = 1.0;
        gbcBar.weighty = 1.0;
        gbcBar.anchor = GridBagConstraints.WEST;

        returnButton.setFocusPainted(false);
        addPlaceButton.setFocusPainted(false);
        deletePlaceButton.setFocusPainted(false);
        saveToPDFButton.setFocusPainted(false);

        barPanel.add(returnButton, gbcBar);

        gbcBar.gridx = 1;
        gbcBar.anchor = GridBagConstraints.CENTER;
        barPanel.add(saveToPDFButton, gbcBar);

        gbcBar.gridx = 2;
        gbcBar.anchor = GridBagConstraints.EAST;
        barPanel.add(deletePlaceButton, gbcBar);

        gbcBar.gridx = 3;
        gbcBar.anchor = GridBagConstraints.EAST;
        barPanel.add(addPlaceButton, gbcBar);

        mainPanel.add(barPanel, BorderLayout.SOUTH);

        MainMenu.buttonResize(barPanel, new JButton[]{returnButton, addPlaceButton, deletePlaceButton, saveToPDFButton});

        /* ---------- BUTTON ACTIONS ---------- */

        prevButton.addActionListener(e -> {
            currentDay--;
            if(currentDay < 0) currentDay = POLISH_DAYS.length-1;
            updateDay();
        });

        nextButton.addActionListener(e -> {
            currentDay++;
            if(currentDay >= POLISH_DAYS.length) currentDay = 0;
            updateDay();
        });

        returnButton.addActionListener(e -> parent.showCard("SELECT"));

        addPlaceButton.addActionListener(e -> {
            JLabel messageLabel = new JLabel("Nazwa miejsca:");
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            JTextField textField = new JTextField(15);

            JPanel panel = new JPanel(new BorderLayout(5,5));
            panel.add(messageLabel, BorderLayout.NORTH);
            panel.add(textField, BorderLayout.CENTER);

            Font oldButtonFont = UIManager.getFont("OptionPane.buttonFont");
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));

            String[] options = {"Anuluj", "Zapisz"};

            int result;
            try {
                result = JOptionPane.showOptionDialog(
                        BreakManagerGUI.this,
                        panel,
                        "Dodaj miejsce",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[1]
                );
            } finally {
                UIManager.put("OptionPane.buttonFont", oldButtonFont);
            }

            if (result == 1) {
                String place = textField.getText().trim();

                if (!place.isEmpty()) {
                    List<String> places = breakManager.getPlaces();
                    if (places.contains(place)) {
                        showWarningMessage("To miejsce już istnieje.");
                    } else {
                        breakManager.addPlace(place);
                        breakManager.savePlaces();
                        buildDayPanels();
                    }
                }
            }
        });

        deletePlaceButton.addActionListener(e -> {
            List<String> places = breakManager.getPlaces();
            if (places.size() <= 1) {
                showWarningMessage("Musi pozostać przynajmniej jedno miejsce.");
                return;
            }

            JLabel messageLabel = new JLabel("Wybierz miejsce do usunięcia:");
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JComboBox<String> comboBox = new JComboBox<>(places.toArray(new String[0]));

            JPanel panel = new JPanel(new BorderLayout(5,5));
            panel.add(messageLabel, BorderLayout.NORTH);
            panel.add(comboBox, BorderLayout.CENTER);

            Font oldButtonFont = UIManager.getFont("OptionPane.buttonFont");
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));

            String[] options = {"Anuluj", "Usuń"};

            int result;
            try {
                result = JOptionPane.showOptionDialog(
                        BreakManagerGUI.this,
                        panel,
                        "Usuń miejsce",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[1]
                );
            } finally {
                UIManager.put("OptionPane.buttonFont", oldButtonFont);
            }

            if (result == 1) {
                String placeToRemove = comboBox.getSelectedItem().toString();
                breakManager.removePlace(placeToRemove);
                breakManager.savePlaces();
                breakManager.saveBreaks();
                buildDayPanels();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                MainMenu.buttonResize(barPanel, new JButton[]{returnButton, addPlaceButton, deletePlaceButton});
            }
        });

        saveToPDFButton.addActionListener(e -> {
            try {
                // 1️⃣ Pobieramy obrazy paneli
                List<BufferedImage> screenshots = new ArrayList<>();
                for (Component comp : breakPanel.getComponents()) {
                    if (comp instanceof JPanel) {
                        JPanel dayPanel = (JPanel) comp;
                        if (dayPanel.getName() != null) {
                            BufferedImage img = new BufferedImage(dayPanel.getWidth(), dayPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                            Graphics2D g2 = img.createGraphics();
                            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            dayPanel.paint(g2);
                            g2.dispose();
                            screenshots.add(img);
                        }
                    }
                }

                if (screenshots.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Brak paneli do zapisania!", "Błąd", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 2️⃣ Tworzymy PrinterJob
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                pf.setOrientation(PageFormat.LANDSCAPE);

                job.setPrintable((graphics, pageFormat, pageIndex) -> {
                    if (pageIndex >= screenshots.size()) return Printable.NO_SUCH_PAGE;

                    BufferedImage img = screenshots.get(pageIndex);

                    // Obrót do landscape jeśli obraz pionowy
                    if (img.getHeight() > img.getWidth()) {
                        BufferedImage rotated = new BufferedImage(img.getHeight(), img.getWidth(), img.getType());
                        Graphics2D g2 = rotated.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        g2.translate(rotated.getWidth() / 2.0, rotated.getHeight() / 2.0);
                        g2.rotate(Math.toRadians(90));
                        g2.translate(-img.getWidth() / 2.0, -img.getHeight() / 2.0);

                        g2.drawImage(img, 0, 0, null);
                        g2.dispose();
                        img = rotated;
                    }

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    double pageWidth = pageFormat.getImageableWidth();
                    double pageHeight = pageFormat.getImageableHeight();

                    // Skalowanie proporcjonalne, maksymalnie wypełniające stronę
                    double scale = Math.min(pageWidth / img.getWidth(), pageHeight / img.getHeight());

                    double offsetX = (pageWidth - img.getWidth() * scale) / 2;
                    double offsetY = (pageHeight - img.getHeight() * scale) / 2;

                    g2d.translate(pageFormat.getImageableX() + offsetX, pageFormat.getImageableY() + offsetY);
                    g2d.scale(scale, scale);
                    g2d.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);

                    return Printable.PAGE_EXISTS;
                }, pf);

                // 3️⃣ Pokaż dialog drukowania
                if (job.printDialog()) {
                    job.print();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Błąd przy drukowaniu: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void showWarningMessage(String text) {
        JLabel messageLabel = new JLabel(text);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        Font oldButtonFont = UIManager.getFont("OptionPane.buttonFont");
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));

        try {
            JOptionPane.showMessageDialog(
                    this,
                    messageLabel,
                    "Błąd",
                    JOptionPane.WARNING_MESSAGE
            );
        } finally {
            UIManager.put("OptionPane.buttonFont", oldButtonFont);
        }
    }

    private void buildDayPanels() {
        breakPanel.removeAll();

        for (int i = 0; i < POLISH_DAYS.length; i++) {
            String dayName = POLISH_DAYS[i];
            DayOfWeek dayOfWeek = DayOfWeek.of(i + 1); // MONDAY = 1

            JPanel dayPanel = new JPanel(new GridBagLayout());
            dayPanel.setBackground(new Color(240,240,240));

            dayPanel.setName(dayName);

            addGridForDay(dayPanel, dayOfWeek);

            breakPanel.add(dayPanel, dayName);
        }

        breakPanel.revalidate();
        breakPanel.repaint();
        updateDay();
    }

    private void addGridForDay(JPanel dayPanel, DayOfWeek dayOfWeek) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;

        List<String> places = breakManager.getPlaces();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        dayPanel.add(new JLabel(""), gbc);

        for (int col = 0; col < places.size(); col++) {
            gbc.gridx = col + 1;
            JLabel header = new JLabel(places.get(col), SwingConstants.CENTER);
            header.setFont(new Font("Arial", Font.BOLD, 20));
            header.setBorder(new LineBorder(Color.BLACK,1));
            dayPanel.add(header, gbc);
        }

        for (int row = 0; row < BREAK_LABELS.length; row++) {
            gbc.gridy = row + 1;
            gbc.gridx = 0;
            JLabel timeLabel = new JLabel(BREAK_LABELS[row], SwingConstants.CENTER);
            timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
            timeLabel.setBorder(new LineBorder(Color.BLACK,1));
            dayPanel.add(timeLabel, gbc);

            for (int col = 0; col < places.size(); col++) {
                gbc.gridx = col + 1;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;

                String place = places.get(col);
                LocalTime start = BREAK_STARTS[row];
                int duration = BREAK_DURATIONS_MIN[row];

                Break breakModule = breakManager.getOrCreateBreak(dayOfWeek, start, place, duration);
                BreakGUI cell = new BreakGUI(breakModule, teacherManager, breakManager);
                dayPanel.add(cell, gbc);
            }
        }
    }

    private void updateDay() {
        dayLabel.setText(POLISH_DAYS[currentDay]);
        cardLayout.show(breakPanel, POLISH_DAYS[currentDay]);
    }

}