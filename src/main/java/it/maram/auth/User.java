package it.maram.auth;

import it.maram.utils.NamedClasses;

import javax.security.auth.login.AccountExpiredException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface User extends NamedClasses {
    void load();
    String serialize();
    boolean login(final char[] pwd, String algo) throws AccountExpiredException, NoSuchAlgorithmException, InvalidKeySpecException;
    boolean isLogged();
    boolean hasOTP();
    String getToken();
    String getName();
    int getAccessLevel();
    boolean isActive();
    String smallSerialize();
    String[] getCustomParams();
}
