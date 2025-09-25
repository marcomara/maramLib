package it.maram.GUI.Login;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import it.maram.GUI.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField pwdField;
    private JButton done;
    private JButton cancel;
    JLabel usr;
    JLabel pwd;
    public LoginFrame(){
        this(null, null);
    }
    public LoginFrame(Font font){
        this(null, font);
    }
    public LoginFrame(Image icon){
        this(icon, null);
    }
    public LoginFrame(@Nullable Image icon,@Nullable Font font){
        setTitle("Login");
        if(icon != null) setIconImage(icon);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setPreferredSize(new Dimension(325,120));
        container.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        JPanel data = new JPanel();
        JPanel opt = new JPanel();
        data.setLayout(null);
        opt.setLayout(new FlowLayout(FlowLayout.RIGHT, 5,5));
        userField = new JTextField();
        pwdField = new JPasswordField();
        usr = new JLabel("Username ");
        pwd = new JLabel("Password ");
        Font f;
        if(font == null) f = new Font("Arial", Font.BOLD, 14);
        else f = font;
        done = new JButton("Login");
        cancel = new JButton("Cancel");
        done.setPreferredSize(new Dimension(75,30));
        cancel.setPreferredSize(new Dimension(75,30));
        opt.setPreferredSize(new Dimension(300,40));
        data.setPreferredSize(new Dimension(315, 75));
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
    public char[] getPWD(){
        return this.pwdField.getPassword();
    }
    public void setClosingOption(WindowListener wl){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(wl);
    }
    public void setCancelButtonAction(ActionListener al){
        cancel.addActionListener(al);
    }
}
