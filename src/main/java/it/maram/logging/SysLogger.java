package it.maram.logging;

import java.io.*;

public class SysLogger extends PrintStream {
    private FileWriter fw;
    private PrintStream ops;
    public SysLogger(PrintStream out, File f) {
        super(out);
        ops = out;
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdir();
        }
        try {
            f.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            fw = new FileWriter(f);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void println(String x) {
        super.println(x);
            try {
                fw.write(x + "\n");
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    @Override
    public void println(int x) {
        super.println(x);
        try {
            fw.write(x + "\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void println(long x) {
        super.println(x);
        try {
            fw.write(x + "\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintStream getOriginalPrintStream() {
        return ops;
    }
    public void finish() throws IOException {
        fw.close();
    }
}
