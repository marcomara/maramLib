package it.maram.utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FilesUtils {
    public static File getFileGUI(File initialFolder, JFrame parent, FileFilter... fileFilters){
        JFileChooser jfc = new JFileChooser(initialFolder);
        for(FileFilter ff : fileFilters){
            jfc.setFileFilter(ff);
        }
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showOpenDialog(parent);
        return jfc.getSelectedFile()!=null ? jfc.getSelectedFile():null;
    }
}
