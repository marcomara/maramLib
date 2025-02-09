package it.maram.GUI;

import javax.swing.*;
import java.awt.*;

public class GuiUtils {
    public static void setCentered(JFrame frame){
        int height = frame.getHeight();
        int width = frame.getWidth();
        int resX = Toolkit.getDefaultToolkit().getScreenSize().width;
        int resY = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setBounds((resX/2)-(width/2),(resY/2)-(height/2), width,height );
    }
}
