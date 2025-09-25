package it.maram.utils;

import javax.swing.*;

public class OSUtils {
    public static void checkOS(String osName){
        if (!System.getProperty("os.name").toLowerCase().startsWith(osName)) {
            if (JOptionPane.showConfirmDialog(null,
                    "This program was created for" + osName + ", using another os can lead to bugs or errors, do you wish to continue?",
                    "Warning", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION) {
                System.exit(1);
            }
        }
    }
}
