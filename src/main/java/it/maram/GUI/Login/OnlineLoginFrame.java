package it.maram.GUI.Login;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import it.maram.GUI.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class OnlineLoginFrame extends JFrame {
    public enum State{
        ONLINE,
        OFFLINE,
        CONNECTING;
    }
    private JTextField userField;
    private JPasswordField pwdField;
    private JButton done;
    private JButton cancel;
    private JButton reload;
    private JLabel usr;
    private JLabel pwd;
    private JLabel onlineState;
    public OnlineLoginFrame(boolean online){
        this(null, null, online);
    }
    public OnlineLoginFrame(Font font, boolean online){
        this(null, font, online);
    }
    public OnlineLoginFrame(Image icon, boolean online){
        this(icon, null, online);
    }
    public OnlineLoginFrame(@Nullable Image icon, @Nullable Font font, boolean online){
        setTitle("Login");
        if(icon != null) setIconImage(icon);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(325,120));
        container.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        JPanel data = new JPanel();
        JPanel opt = new JPanel();
        onlineState = new JLabel();
        onlineState.setFont(new Font("Arial", Font.BOLD, 12));
        if(online){
            changeOnlineStatus(State.ONLINE);
        }else{
            changeOnlineStatus(State.OFFLINE);
        }
        data.setLayout(null);
        opt.setLayout(new FlowLayout(FlowLayout.RIGHT, 5,5));
        userField = new JTextField();
        pwdField = new JPasswordField();
        usr = new JLabel("Username ");
        pwd = new JLabel("Password ");
        reload = new JButton();
        Font f;
        if(font == null) f = new Font("Arial", Font.BOLD, 14);
        else f = font;
        done = new JButton("Login");
        cancel = new JButton("Cancel");
        done.setPreferredSize(new Dimension(75,30));
        cancel.setPreferredSize(new Dimension(75,30));
        opt.setPreferredSize(new Dimension(300,40));
        data.setPreferredSize(new Dimension(315, 75));
        reload.setPreferredSize(new Dimension(30,30));
        opt.add(reload);
        opt.add(onlineState);
        opt.add(cancel);
        opt.add(done);
        usr.setFont(f);
        pwd.setFont(f);
        userField.setFont(f);
        pwdField.setFont(f);
        usr.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pwd.setAlignmentX(Component.RIGHT_ALIGNMENT);
        usr.setBounds(5,5,100,30);
        userField.setBounds(110,5,200,30);
        pwd.setBounds(5,40,100,30);
        pwdField.setBounds(110,40,200,30);
        data.add(usr);
        data.add(userField);
        data.add(pwd);
        data.add(pwdField);
        container.add(data);
        container.add(opt);
        add(container);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void setUsernameLabelText(@NotNull String s){
        usr.setText(s);
    }
    public void setPasswordLabelText(@NotNull String s){
        pwd.setText(s);
    }
    public void setDoneButtonText(@NotNull String s){
        done.setText(s);
    }
    public void setCancelButtonText(@NotNull String s){
        cancel.setText(s);
    }
    public void showFrame(){
        pack();
        GUIUtils.setCentered(this);
        setVisible(true);
    }
    public void setDoneButtonAction(ActionListener al){
        done.addActionListener(al);
    }
    public String getUsername(){
        return this.userField.getText();
    }
    public String getPWD(){
        return new String(this.pwdField.getPassword());
    }
    public void setClosingOption(WindowListener wl){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(wl);
    }
    public void setCancelButtonAction(ActionListener al){
        cancel.addActionListener(al);
    }
    public void setReloadButtonAction(ActionListener al){
        reload.addActionListener(al);
    }
    public void setReloadIcon(ImageIcon ico){
        reload.setIcon(ico);
    }
    public void changeOnlineStatus(State s){
        switch (s){
            case ONLINE: onlineState.setText("Online");
                onlineState.setForeground(new Color(68,120,52));
                repaint();
                break;
            case OFFLINE:onlineState.setText("Offline");
                onlineState.setForeground(Color.RED);
                repaint();
                break;
            case CONNECTING:onlineState.setText("Connecting...");
                onlineState.setForeground(Color.BLACK);
                repaint();
                break;
        }
    }
}
