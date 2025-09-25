package it.maram.auth;

import it.maram.logging.UserFileException;
import it.maram.logging.UserNotLoggedException;

import javax.security.auth.login.AccountExpiredException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class PracticalUser implements User{
    private boolean logged;
    private byte[] token;
    private final String username;
    private final String values;
    private GenericUserType type = null;
    private Date expiration = null;
    public PracticalUser(String username, String values){
        this.username = username;
        this.values = values;
    }
    @Override
    public void load() {
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
            }
            if(type == null || expiration == null) throw new UserFileException("File must contain user type and credentials expiration date");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serialize(){
        return null;
    }

    @Override
    public boolean login(char[] pwd, String algo) throws AccountExpiredException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.logged = true;
        return true;
    }

    @Override
    public boolean isLogged() {
        return this.logged;
    }

    @Override
    public boolean hasOTP() {
        return token.length>0;
    }

    @Override
    public String getToken() {
        return new String(token);
    }

    @Override
    public String getName() {
        return this.username;
    }

    @Override
    public int getAccessLevel() {
        return this.type.get();
    }

    @Override
    public boolean isActive() {
        return expiration.after(Calendar.getInstance().getTime());
    }

    @Override
    public String smallSerialize() {
        return null;
    }
    @Override
    public String[] getCustomParams(){
        return null;
    }
    public final String getValues(){
        return this.values;
    }
    public static PracticalUser loadFromJson(String jsonContent){
        final String[] lines = jsonContent.split("\n");
        String name = null,
                values = null;
        for(String s : lines){
            s = s.trim();
            if(s.startsWith(UsersLoader.usr))
                name = s.split(":")[1].replace(",", "").trim();
            if(s.startsWith(UsersLoader.s2))
                values = s.substring(UsersLoader.s2.length()+1);
        }
        if(name == null || values == null) throw new UserFileException("User file must contains " + UsersLoader.usr + " and " + UsersLoader.s2 + " fields!");
        return new PracticalUser(name, values);
    }
}
