package it.maram.auth.UPAUE;

import it.maram.auth.OTP.Authenticator;
import it.maram.auth.Password;
import it.maram.auth.GenericUserType;
import it.maram.auth.UsersLoader;
import it.maram.logging.UserFileException;
import it.maram.logging.UserNotLoggedException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class User implements it.maram.auth.User {

    private final String username;
    private final String pwdhash;
    private final String values;
    private boolean logged = false;
    private GenericUserType type = null;
    private char[] pass;
    private Date expiration = null;
    private byte[] token = null;
    private String[] customParams;
    public User(String username, String pwdhash, String values) {
        this.username = username;
        this.pwdhash = pwdhash;
        this.values = values;
    }
    public boolean login(final char[] pwd, String algo) throws NoSuchAlgorithmException, InvalidKeySpecException {
            if(Password.verifyPassword(pwd, pwdhash, algo)){
                pass = pwd;
                logged = true;
                return true;
            }else return false;
    }
    private void loginIgnore(final char[] pwd, String algo){
        try{
            if(Password.verifyPassword(pwd, pwdhash, algo)){
                pass = pwd;
                logged = true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            //throw new RuntimeException(e);
        }
    }
    public final boolean isActive(){
        return expiration.after(Calendar.getInstance().getTime());
    }
    public void load() throws UserNotLoggedException, UserFileException {
        if(!logged) throw new UserNotLoggedException("User " + username + " is not logged");
        try{
            final String[] vals = values.split("\\|");
            for(final String s : vals){
                String[] ss = s.split(":");
                if (ss[0].equals("token")){
                    if(ss.length == 2) token = ss[1].getBytes();
                    else token = new byte[0];
                }
                if (ss[0].equals("type")) type = GenericUserType.fromString(ss[1]);
                if (ss[0].equals("expiration")) expiration = UsersLoader.format.parse(ss[1]);
                if (s.startsWith("customParams")){
                    String[] tmp = s.split(":");
                    if(tmp.length==1) customParams = new String[0];
                    else{
                        String secrets = tmp[1];
                        customParams = secrets.split(";");
                    }
                }
            }
            if(type == null || expiration == null) throw new UserFileException("File must contain user type and credentials expiration date");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isLogged(){
        return this.logged;
    }
    public final String serialize(){
        return "{\n" +
                "\t" + UsersLoader.usr + ":" + username + ",\n" +
                "\t" + UsersLoader.s1 + ":" + pwdhash + ",\n" +
                "\t" + UsersLoader.s2 + ":" + values + "\n" +
                "}\n";
    }
    public boolean hasOTP(){
        return this.token.length>0;
    }
    public String getToken(){
        return new String(token);
    }
    public String getName() {
        return username;
    }

    @Override
    public int getAccessLevel() {
        return this.type.get();
    }
    public String smallSerialize(){
        return "{\n" +
                "\t" + UsersLoader.usr + ":" + username + ",\n" +
                "\t" + UsersLoader.s2 + ":" + values + "\n" +
                "}\n";
    }
    public String[] getCustomParams(){
        return this.customParams;
    }
    public static User createUser(String username, final char[] password, final String algo,
                                  GenericUserType type, Date expirationDate, boolean generateOTPToken, String[] customParams)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        final String pwdHash = Password.getPwdSecret(password, algo);
        final byte[] token;
        if (generateOTPToken) token = Authenticator.createEncodedPhrase();
        else token = new byte[0];
        final StringBuilder cps = new StringBuilder();
        for (String s : customParams){
            cps.append(s).append(";");
        }
        final String values = "token:"+new String(token)+"|type:"+type.toString()+"|expiration:"+UsersLoader.format.format(expirationDate)+"|customParams:"+cps;
        final User user = new User(username, pwdHash, values);
        user.loginIgnore(password, algo);
        user.load();
        return user;
    }
}
