package GUIModules;

import DataModules.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.geom.Rectangle2D;

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
    private JButton asignAutomaticallyButton = new JButton("Przypisz automatycznie");

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
        gbcBar.anchor = GridBagConstraints.CENTER;
        barPanel.add(asignAutomaticallyButton, gbcBar);

        gbcBar.gridx = 3;
        gbcBar.anchor = GridBagConstraints.EAST;
        barPanel.add(deletePlaceButton, gbcBar);

        gbcBar.gridx = 4;
        gbcBar.anchor = GridBagConstraints.EAST;
        barPanel.add(addPlaceButton, gbcBar);

        mainPanel.add(barPanel, BorderLayout.SOUTH);

        MainMenu.buttonResize(barPanel, new JButton[]{returnButton, addPlaceButton, deletePlaceButton, saveToPDFButton,  asignAutomaticallyButton});

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
            if (places == null || places.isEmpty()) {
                showWarningMessage("Brak miejsc do usunięcia.");
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
                List<String> places = breakManager.getPlaces();
                if (places == null || places.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Brak miejsc do zapisania!", "Błąd", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                pf.setOrientation(PageFormat.LANDSCAPE);

                job.setPrintable((graphics, pageFormat, pageIndex) -> {
                    if (pageIndex >= POLISH_DAYS.length) return Printable.NO_SUCH_PAGE;

                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                    double ix = pageFormat.getImageableX();
                    double iy = pageFormat.getImageableY();
                    double pageWidth = pageFormat.getImageableWidth();
                    double pageHeight = pageFormat.getImageableHeight();

                    String dayName = POLISH_DAYS[pageIndex];
                    DayOfWeek dayOfWeek = DayOfWeek.of(pageIndex + 1);

                    int titleHeight = 50;
                    double tableTop = iy + titleHeight;
                    double tableHeight = pageHeight - titleHeight;

                    int rows = BREAK_LABELS.length + 1;
                    int cols = places.size() + 1;
                    double colWidth = pageWidth / cols;
                    double rowHeight = tableHeight / rows;

                    // Day title
                    g2d.setFont(new Font("Arial", Font.BOLD, 36));
                    FontMetrics fmTitle = g2d.getFontMetrics();
                    g2d.drawString(dayName, (int) (ix + (pageWidth - fmTitle.stringWidth(dayName)) / 2), (int) (iy + fmTitle.getAscent()));

                    g2d.setFont(new Font("Arial", Font.PLAIN, 11));

                    // Grid and content
                    for (int row = 0; row < rows; row++) {
                        for (int col = 0; col < cols; col++) {
                            double x = ix + col * colWidth;
                            double y = tableTop + row * rowHeight;
                            g2d.setColor(Color.BLACK);
                            g2d.drawRect((int) x, (int) y, (int) colWidth, (int) rowHeight);

                            if (row == 0) {
                                if (col == 0) {
                                    // empty corner
                                } else {
                                    String place = places.get(col - 1);
                                    drawPrintPlaceHeader(g2d, place, (int) x, (int) y, (int) colWidth, (int) rowHeight);
                                    g2d.setFont(new Font("Arial", Font.PLAIN, 11));
                                }
                            } else {
                                int breakRow = row - 1;
                                if (col == 0) {
                                    g2d.setFont(new Font("Arial", Font.BOLD, 11));
                                    drawCenteredString(g2d, BREAK_LABELS[breakRow], (int) x, (int) y, (int) colWidth, (int) rowHeight);
                                    g2d.setFont(new Font("Arial", Font.PLAIN, 11));
                                } else {
                                    String place = places.get(col - 1);
                                    Break breakModule = breakManager.getOrCreateBreak(dayOfWeek, BREAK_STARTS[breakRow], place, BREAK_DURATIONS_MIN[breakRow]);
                                    String cellText = formatTeachersForCell(breakModule);
                                    String extraText = formatExtraPlaceForCell(breakModule);
                                    drawPrintBreakCell(g2d, (int) x, (int) y, (int) colWidth, (int) rowHeight,
                                            breakModule.getBackgroundColor(), cellText, extraText);
                                    g2d.setFont(new Font("Arial", Font.PLAIN, 11));
                                }
                            }
                        }
                    }

                    return Printable.PAGE_EXISTS;
                }, pf);

                if (job.printDialog()) {
                    job.print();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Błąd przy drukowaniu: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        asignAutomaticallyButton.addActionListener(e -> {
            new AutomaticTeacherAsigner(breakManager, teacherManager, settingsManager);
            breakManager.saveBreaks();
            breakManager.updateRemainingDutyMinutesForTeachers(teacherManager);
            buildDayPanels();
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
            DayOfWeek dayOfWeek = DayOfWeek.of(i + 1);

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

    private static String formatTeachersForCell(Break breakModule) {
        List<Teacher> teachers = breakModule.getTeachers();
        if (teachers == null || teachers.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Teacher t : teachers) {
            if (sb.length() > 0) sb.append("\n");
            sb.append(t.getName()).append(" ").append(t.getSurname());
        }
        return sb.toString();
    }

    private static String formatExtraPlaceForCell(Break breakModule) {
        ExtraPlace ep = breakModule.getExtraPlace();
        if (ep == null || !ep.isConfigured()) {
            return "";
        }
        return ep.getName().trim() + "\n" + ep.getTeacher().getName() + " " + ep.getTeacher().getSurname();
    }

    private static List<String> nonEmptyLines(String text) {
        if (text == null || text.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> out = new ArrayList<>();
        for (String raw : text.split("\n")) {
            String t = raw.trim();
            if (!t.isEmpty()) {
                out.add(t);
            }
        }
        return out;
    }

    /**
     * Fills the cell, then draws main teachers (black) and extra place (red) with a font size that fits the cell.
     */
    private static void drawPrintBreakCell(Graphics2D g2d, int x, int y, int w, int h,
                                           Color fillColor, String mainText, String extraText) {
        g2d.setColor(fillColor);
        g2d.fillRect(x + 1, y + 1, w - 2, h - 2);

        List<String> mainLines = nonEmptyLines(mainText);
        List<String> extraLines = nonEmptyLines(extraText);
        if (mainLines.isEmpty() && extraLines.isEmpty()) {
            return;
        }

        int pad = 2;
        int innerX = x + pad;
        int innerY = y + pad;
        int innerW = Math.max(1, w - 2 * pad);
        int innerH = Math.max(1, h - 2 * pad);
        if (innerW < 4 || innerH < 4) {
            return;
        }

        int gapPx = (!mainLines.isEmpty() && !extraLines.isEmpty()) ? 2 : 0;

        Shape oldClip = g2d.getClip();
        g2d.clip(new Rectangle2D.Double(x + 1, y + 1, w - 2, h - 2));

        Font oldFont = g2d.getFont();
        try {
            int fontSize = 9;
            FontMetrics fm;
            int lh;
            int neededH;
            while (true) {
                g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
                fm = g2d.getFontMetrics();
                lh = Math.max(1, fm.getHeight());
                neededH = mainLines.size() * lh + gapPx + extraLines.size() * lh;
                if (neededH <= innerH || fontSize <= 5) {
                    break;
                }
                fontSize--;
            }

            int totalContentH = mainLines.size() * lh + gapPx + extraLines.size() * lh;
            int startBaselineY = innerY + fm.getAscent() + Math.max(0, (innerH - totalContentH) / 2);

            int cy = startBaselineY;
            g2d.setColor(Color.BLACK);
            for (String line : mainLines) {
                String t = truncateWithEllipsis(g2d, line, innerW);
                int tx = innerX + (innerW - fm.stringWidth(t)) / 2;
                g2d.drawString(t, tx, cy);
                cy += lh;
            }
            if (gapPx > 0) {
                cy += gapPx;
            }
            g2d.setColor(Color.RED);
            for (String line : extraLines) {
                String t = truncateWithEllipsis(g2d, line, innerW);
                int tx = innerX + (innerW - fm.stringWidth(t)) / 2;
                g2d.drawString(t, tx, cy);
                cy += lh;
            }
        } finally {
            g2d.setFont(oldFont);
            g2d.setClip(oldClip);
        }
    }

    /**
     * Place column title for print/PDF: scales down Arial bold until the name fits, then truncates with "..." if needed.
     */
    private static void drawPrintPlaceHeader(Graphics2D g2d, String text, int x, int y, int w, int h) {
        if (text == null) {
            text = "";
        }
        int padX = 4;
        int maxW = Math.max(1, w - 2 * padX);
        Shape oldClip = g2d.getClip();
        Font oldFont = g2d.getFont();
        Color oldColor = g2d.getColor();
        try {
            g2d.setColor(Color.BLACK);
            g2d.clip(new Rectangle2D.Double(x + 1, y + 1, Math.max(1, w - 2), Math.max(1, h - 2)));

            String toDraw = text;
            int chosenSize = 5;
            boolean fullTextFits = false;
            for (int fontSize = 12; fontSize >= 5; fontSize--) {
                g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
                FontMetrics fm = g2d.getFontMetrics();
                if (fm.stringWidth(text) <= maxW) {
                    toDraw = text;
                    chosenSize = fontSize;
                    fullTextFits = true;
                    break;
                }
            }
            g2d.setFont(new Font("Arial", Font.BOLD, chosenSize));
            FontMetrics fm = g2d.getFontMetrics();
            if (!fullTextFits) {
                toDraw = truncateWithEllipsis(g2d, text, maxW);
            }
            int tx = x + (w - fm.stringWidth(toDraw)) / 2;
            int ty = y + (h + fm.getAscent()) / 2 - fm.getDescent();
            g2d.drawString(toDraw, tx, ty);
        } finally {
            g2d.setFont(oldFont);
            g2d.setColor(oldColor);
            g2d.setClip(oldClip);
        }
    }

    private static void drawCenteredString(Graphics2D g2d, String s, int x, int y, int w, int h) {
        FontMetrics fm = g2d.getFontMetrics();
        int tx = x + (w - fm.stringWidth(s)) / 2;
        int ty = y + (h + fm.getAscent()) / 2 - fm.getDescent();
        g2d.drawString(s, tx, ty);
    }

    private static String truncateWithEllipsis(Graphics2D g2d, String s, int maxWidth) {
        FontMetrics fm = g2d.getFontMetrics();
        if (fm.stringWidth(s) <= maxWidth) return s;
        String ellipsis = "...";
        int ew = fm.stringWidth(ellipsis);
        for (int i = s.length() - 1; i > 0; i--) {
            if (fm.stringWidth(s.substring(0, i) + ellipsis) <= maxWidth) {
                return s.substring(0, i) + ellipsis;
            }
        }
        return ellipsis;
    }

}