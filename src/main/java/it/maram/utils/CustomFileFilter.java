package it.maram.utils;

import com.sun.istack.internal.Nullable;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class CustomFileFilter extends FileFilter {
    private final String descr;
    private final String ext;
    private Integer i  = null;
    public CustomFileFilter(String description, String extension){
        this.descr=description;
        this.ext=extension;
    }
    public CustomFileFilter(String extension){
        this.ext=extension;
        this.descr=extension+" file";
    }
    public CustomFileFilter(String description, String extension, int i){
        this.descr=description;
        this.ext=extension;
        this.i=i;
    }
    @Override
    public boolean accept(File f) {
        if(f.isDirectory()) return true;
        return f.getName().endsWith(ext);
    }
    @Override
    public String getDescription() {
        return this.descr;
    }
    @Nullable
    public Integer getI(){
        return this.i;
    }
}
