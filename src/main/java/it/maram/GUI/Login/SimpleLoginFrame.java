package it.maram.GUI.Login;

import it.maram.auth.User;
import it.maram.auth.OTP.Authenticator;
import it.maram.auth.OTP.OTPAuth;
import it.maram.logging.ExceptionHandler;
import it.maram.utils.ListUtils;

import javax.security.auth.login.AccountExpiredException;
import javax.swing.*;
import java.awt.*;
import java.rmi.UnexpectedException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class SimpleLoginFrame {
    public static final int exit = 0;
    public static final int tryAgain = 1;
    public static int simpleLogin(List<User> users, String algo, String title, Icon icon, Component parent, int closingOption) throws UnexpectedException {
        JTextField username = new JTextField();
        JPasswordField pwd = new JPasswordField();
        JLabel usernameL = new JLabel("Username:");
        JLabel pwdL = new JLabel("Password:");
        JComponent[] components = new JComponent[]{
                usernameL, username, pwdL, pwd
        };
        boolean canContinue = false;
        int i;
        do{
            i = JOptionPane.showConfirmDialog(parent, components, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
            if(ListUtils.contains(users, username.getText())){
                final User user = ListUtils.get(users, username.getText());
                assert user != null;
                try {
                    if (user.login(pwd.getPassword(), algo)) {
                        user.load();
                        if (user.hasOTP()) {
                            canContinue = OTPAuth.gui(new String(Authenticator.encodeSecret32(user.getToken())), true, 2);
                        } else canContinue = true;
                    } else {
                        i = -1;
                        JOptionPane.showMessageDialog(parent, "Incorrect password", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }catch (AccountExpiredException e){
                    JOptionPane.showMessageDialog(parent, "Account expired", "Warning", JOptionPane.WARNING_MESSAGE);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    ExceptionHandler.handleLoudly(e);
                }
            }else{
                i = -1;
                JOptionPane.showMessageDialog(parent, "User \"" + username.getText() + "\" does not exist", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }while(i < 0);
        if(canContinue) return ListUtils.get(users, username.getText()).getAccessLevel();
        else if(closingOption == 0) System.exit(0);
        else if (closingOption == 1) return simpleLogin(users, algo, title, icon, parent, closingOption);
        throw new UnexpectedException("Unexpected exception");
    }
}
