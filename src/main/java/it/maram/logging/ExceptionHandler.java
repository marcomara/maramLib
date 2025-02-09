package it.maram.logging;

import javax.swing.*;

public class ExceptionHandler {
    public static void handleLoudly(Exception e){
        handleSilently(e);
        JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + " : " + e.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
    }
    public static void handleLoudlyDebug(Exception e, String msg){
        handleSilently(e);
        JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + " : " + e.getMessage() + "\nFrom " + msg, "An error occurred", JOptionPane.ERROR_MESSAGE);
    }
    public static void handleSilently(Exception e){
        handleNoTrace(e);
        for(StackTraceElement el : e.getStackTrace()) System.out.println(el.toString());
    }
    public static void handleNoTrace(Exception e){
        System.out.println("An error occurred");
        System.out.println(e.getClass().getSimpleName() + " : " + e.getMessage());
    }
    public static void handleSoftly(Exception e){
        handleSilently(e);
        JOptionPane.showMessageDialog(null, e.getClass().getSimpleName() + " : " + e.getMessage(), "Warning!", JOptionPane.WARNING_MESSAGE);
    }
}
