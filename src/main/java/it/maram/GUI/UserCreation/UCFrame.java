package it.maram.GUI.UserCreation;

import com.sun.istack.internal.Nullable;
import it.maram.GUI.DateSelector;
import it.maram.GUI.GUIUtils;
import it.maram.auth.OTP.OTPAuth;
import it.maram.auth.UPAE.User;
import it.maram.auth.GenericUserType;
import it.maram.logging.UserLevelException;
import it.maram.logging.ExceptionHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class UCFrame extends JFrame {
    private JTextField username;
    private JPasswordField pass;
    private JPasswordField cPass;

    public UCFrame(Image icon, String title, File usersFile, String algo, Component parent, int level, @Nullable String issuer){
        setIconImage(icon);
        setTitle(title);
        JLabel user = new JLabel("Username: ");
        JLabel passL = new JLabel("Password: ");
        JLabel cPassL = new JLabel("Confirm password: ");
        JLabel usrT = new JLabel("User auth lvl: ");
        JLabel customParamsL = new JLabel("Custom parameters (one per line):");
        user.setPreferredSize(new Dimension(100,30));
        passL.setPreferredSize(new Dimension(100,30));
        cPassL.setPreferredSize(new Dimension(100,30));
        usrT.setPreferredSize(new Dimension(100,30));
        customParamsL.setHorizontalAlignment(SwingConstants.LEFT);
        JComboBox<GenericUserType> types;
        if(level == 0){
            types = new JComboBox<>(new GenericUserType[]{GenericUserType.DEVELOPER, GenericUserType.ADMIN, GenericUserType.USER});
        }else if(level == 1){
            types = new JComboBox<>(new GenericUserType[]{GenericUserType.ADMIN, GenericUserType.USER});
        }else throw new UserLevelException("User permission level is too low");
        JCheckBox useOTP = new JCheckBox("Generate OTP code");
        DateSelector expiration = new DateSelector("Expiration date: ", 300,40);
        JTextArea customParams = new JTextArea();
        JButton done = new JButton("Done");
        username = new JTextField();
        pass = new JPasswordField();
        cPass = new JPasswordField();
        username.setPreferredSize(new Dimension(200,25));
        pass.setPreferredSize(new Dimension(200,25));
        cPass.setPreferredSize(new Dimension(200,25));
        types.setPreferredSize(new Dimension(new Dimension(200,25)));
        useOTP.setPreferredSize(new Dimension(200,25));
        expiration.setPreferredSize(new Dimension(200,25));
        customParams.setFont(new Font("Arial", Font.PLAIN, 12));
        customParams.setLineWrap(false);
        types.setToolTipText("Set the level of authority the user will receive");
        useOTP.setToolTipText("Set if the user will be prompted to insert an OTP code when trying to access");
        expiration.setToolTipText("The expiration date after which the user credentials will not work anymore (expressed in dd-MM-yyyy)");
        customParams.setToolTipText("Insert custom params, one per line");
        done.setPreferredSize(new Dimension(70, 40));
        done.setEnabled(false);
        cPass.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                done.setEnabled(Arrays.equals(pass.getPassword(), cPass.getPassword()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                done.setEnabled(Arrays.equals(pass.getPassword(), cPass.getPassword()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                done.setEnabled(Arrays.equals(pass.getPassword(), cPass.getPassword()));
            }
        });
        done.addActionListener(a->{
            try {
                final User newUser = User.createUser(username.getText(),
                        pass.getPassword(),
                        algo,
                        16,
                        (GenericUserType) types.getSelectedItem(),
                        expiration.getDate(),
                        useOTP.isSelected(),
                        customParams.getText().split("\n"));
                if(usersFile.exists()) Files.write(usersFile.toPath(), newUser.serialize().getBytes(), StandardOpenOption.APPEND);
                else Files.write(usersFile.toPath(), newUser.serialize().getBytes(), StandardOpenOption.CREATE_NEW);
                if (useOTP.isSelected()) {
                    JLabel label = new JLabel("This code will no longer be visible after closing this window!\n" + newUser.getToken());
                    JLabel img = new JLabel(OTPAuth.generateQRCode(newUser.getToken(), newUser.getName(), issuer));
                    JComponent[] comps = new JComponent[]{label, img};
                    JOptionPane.showMessageDialog(parent, comps, "Codie OTP (2FA)", JOptionPane.WARNING_MESSAGE);
                }
            }catch (Exception e){
                ExceptionHandler.handleLoudly(e);
            }
            dispose();
        });
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        JPanel usr = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel pwd = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel cpwd = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel lvl = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        JPanel otp = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,5));
        JScrollPane cpsp = new JScrollPane(customParams, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cpsp.setPreferredSize(new Dimension(250,100));
        JPanel cpl = new JPanel(new FlowLayout(FlowLayout.LEFT,5 ,5));
        JPanel cpta = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        usr.add(user); usr.add(username);
        pwd.add(passL); pwd.add(pass);
        cpwd.add(cPassL); cpwd.add(cPass);
        lvl.add(usrT); lvl.add(types);
        otp.add(useOTP);
        cpl.add(customParamsL);
        cpta.add(cpsp);
        buttons.add(done);
        content.add(usr); content.add(pwd); content.add(cpwd); content.add(lvl);
        content.add(otp); content.add(expiration); content.add(cpl); content.add(cpsp); content.add(buttons);
        add(content);
        pack();
        if(parent==null){
            GUIUtils.setCentered(this);
        }else setLocationRelativeTo(parent);
        setVisible(true);
    }
}
