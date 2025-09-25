package it.maram.utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

public class FilesUtils {
    public static File getFileGUI(File initialFolder, JFrame parent, FileFilter... fileFilters){
        JFileChooser jfc = new JFileChooser(initialFolder);
        for(FileFilter ff : fileFilters){
            jfc.setFileFilter(ff);
        }
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showOpenDialog(parent);
        if (jfc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) return jfc.getSelectedFile();
        else return null;
    }
    public static File createFileGUI(File initialFolder, JFrame parent, String defaultFileName, FileFilter... fileFilters) throws IOException {
        JFileChooser jfc = new JFileChooser(initialFolder);
        for (FileFilter ff : fileFilters){
            jfc.setFileFilter(ff);
        }
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setSelectedFile(new File(initialFolder!=null?initialFolder:jfc.getCurrentDirectory(), defaultFileName));
        if (jfc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION){
            jfc.getSelectedFile().createNewFile();
            return jfc.getSelectedFile();
        }
        else return null;
    }
}
