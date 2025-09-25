package it.maram.GUI;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import it.maram.logging.ExceptionHandler;

import javax.swing.*;
import java.awt.*;

public class GUIUtils {
    public static <T extends Window> void setCentered(T frame){
        int height = frame.getHeight();
        int width = frame.getWidth();
        int resX = Toolkit.getDefaultToolkit().getScreenSize().width;
        int resY = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setBounds((resX/2)-(width/2),(resY/2)-(height/2), width,height );
    }
    @Nullable
    public static String[] multiSelectFrom(String[] selection, int selectionLength, @NotNull String title, @Nullable String message, @Nullable Component parent){
        JComboBox<String>[] boxes = new JComboBox[selectionLength];
        for (int i = 0; i<selectionLength; i++){
            boxes[i] = new JComboBox<>(selection);
        }
        if(message != null){
            JComponent[] inputs = new JComponent[selectionLength+1];
            inputs[0] = new JLabel(message);
            System.arraycopy(boxes, 0, inputs, 1, selectionLength + 1 - 1);
            if(JOptionPane.showConfirmDialog(parent, inputs, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION){
                String[] returns = new String[selectionLength];
                for (int i = 0; i< selectionLength; i++){
                    returns[i] = (String) boxes[i].getSelectedItem();
                }
                return returns;
            }else return null;
        }else{
            if(JOptionPane.showConfirmDialog(parent, boxes, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION){
                String[] returns = new String[selectionLength];
                for (int i = 0; i< selectionLength; i++){
                    returns[i] = (String) boxes[i].getSelectedItem();
                }
                return returns;
            }else return null;
        }
    }
    public static void preloadGUI(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.handleLoudly(e);
        }
    }
}
