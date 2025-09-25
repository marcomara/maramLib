package it.maram.auth.UPAE;

import it.maram.auth.AES.AESCrypto;
import it.maram.auth.OTP.Authenticator;
import it.maram.auth.Password;
import it.maram.auth.GenericUserType;
import it.maram.auth.UsersLoader;
import it.maram.logging.ExceptionHandler;
import it.maram.logging.UserFileException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.login.AccountExpiredException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class User implements it.maram.auth.User {
    private final String username;
    private final String secret1;
    private final String secret2;
    private boolean logged = false;
    private char[] pass;
    private byte[] salt;
    private GenericUserType type = null;
    private String newSecret1;
    private String newSecret2;
    private String newUsername;
    private Date expiration = null;
    private byte[] token = null;
    private String[] customParams;
    public User(final String username, final String secret1, final String secret2){
        this.username=username;
        this.secret1=secret1;
        this.secret2=secret2;
    }
    public boolean login(final char[] pwd, String algo) throws AccountExpiredException{
        try {
            if(Password.verifyPassword(pwd, secret1, algo)){
                pass = pwd;
                salt = Password.getSalt(secret1.split(":")[0]);
                logged = true;
                return true;
            }else return false;
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new RuntimeException();
        }
    }
    public final boolean isActive(){
        final Date when = Calendar.getInstance().getTime();
        return expiration.after(when);
    }
    public int getAccessLevel(){
        return this.type.get();
    }
    public void load(){
        if(!logged) return;
        try {
            final String decoded = AESCrypto.decrypt(secret2, Password.generateKey(pass, salt));
            System.out.println(decoded);
            final String[] decA = decoded.split("\\|");
            for(final String s : decA){
                if (s.startsWith("token")){
                    if(s.split(":").length>1) token = s.split(":")[1].getBytes();
                    else token = new byte[0];
                }
                if (s.startsWith("type")) type = GenericUserType.fromString(s.split(":")[1]);
                if (s.startsWith("expiration")) expiration = UsersLoader.format.parse(s.split(":")[1]);
                if (s.startsWith("customParams")){
                    String[] tmp = s.split(":");
                    if(tmp.length==1) customParams = new String[0];
                    else{
                        String secrets = tmp[1];
                        customParams = secrets.split(";");
                    }
                }
            }
            System.out.println(token==null?"null":new String(token) + ", " + type + ", " + expiration);
            if(type==null||expiration==null) throw new UserFileException("File must contain user type and credentials expiration date");
        }catch (Exception e){
            ExceptionHandler.handleLoudly(e);
        }
    }
    public boolean isLogged(){
        return this.logged;
    }
    public final String serialize(){
        return "{\n" +
                "\t" + UsersLoader.usr + ":" + username + ",\n" +
                "\t" + UsersLoader.s1 + ":" + secret1 + ",\n" +
                "\t" + UsersLoader.s2 + ":" + secret2 + "\n" +
                "}\n";
    }
    public boolean hasOTP(){
        return this.token.length>0;
    }
    public String getToken(){
        return new String(token);
    }
    private void loginIgnore(final char[] pwd, String algo){
        try{
            if(Password.verifyPassword(pwd, secret1, algo)){
                pass = pwd;
                salt = Password.getSalt(secret1.split(":")[0]);
                logged = true;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    public String getName() {
        return username;
    }
    public String[] getCustomParams(){
        return this.customParams;
    }
    public String smallSerialize(){
        return "{\n" +
                "\t" + UsersLoader.usr + ":" + username + ",\n" +
                "\t" + UsersLoader.s2 + ":" + secret2 + "\n" +
                "}\n";
    }
    public static User createUser(String username,
                                  final char[] password, final String algo, final int saltLen,
                                  GenericUserType type, Date expirationDate,
                                  boolean generateOTPToken, String[] customParams)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        final String secret1 = Password.getPwdSecret(password, algo, saltLen);
        final byte[] token;
        if(generateOTPToken){
            token = Authenticator.createEncodedPhrase();
        }else token = new byte[0];
        final StringBuilder cps = new StringBuilder();
        for (String s : customParams){
            cps.append(s).append(";");
        }
        final String s2tc = "token:"+new String(token)+"|type:"+type.toString()+"|expiration:"+UsersLoader.format.format(expirationDate)+"|customParams:"+cps;
        final String secret2 = AESCrypto.encrypt(s2tc, Password.generateKey(password, Base64.getDecoder().decode(secret1.split(":")[0])), saltLen);
        final User user = new User(username, secret1, secret2);
        user.loginIgnore(password, algo);
        user.load();
        return user;
    }


}
