import GUIModules.Frame;
import GUIModules.MainMenu;

import javax.swing.*;
import java.awt.*;

public class GUIHandler {
    public GUIHandler(){
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());


            UIManager.put("Button.arc", 14);
            UIManager.put("Button.focusWidth", 0);
            UIManager.put("Panel.background", new Color(245, 245, 245));
            UIManager.put("control", new Color(245, 245, 245));

            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 26));
            UIManager.put("Label.font", new Font("Segoe UI", Font.BOLD, 45));

            UIManager.put("Button.background", new Color(70, 70, 70));
            UIManager.put("Button.hoverBackground", new Color(90, 90, 90));
            UIManager.put("Button.pressedBackground", new Color(50, 50, 50));
            UIManager.put("Button.foreground", new Color(230, 230, 230));
            UIManager.put("Button.borderColor", new Color(100, 100, 100));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(Frame::new);
    }
}
