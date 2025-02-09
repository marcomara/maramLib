package it.maram.auth.User;

import it.maram.logging.ExceptionHandler;
import it.maram.utils.Address;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

public class User{
    public enum UserType{
        DEVELOPER,
        ADMIN,
        USER;

        public String toString(){
            switch (this){
                case USER: return "USER";
                case ADMIN: return "ADMIN";
                case DEVELOPER: return "DEVELOPER";
                default: return "NULL";
            }
        }
        public static UserType fromString(String s){
            switch (s){
                case "USER": return USER;
                case "ADMIN": return ADMIN;
                case "DEV": return DEVELOPER;
                default: return null;
            }
        }
    }
    final Properties prp;
    final UserType type;
    final String username;
    char[] password;
    String name, surname;
    Address address;
    Date birth, registration, expiration;
    final File usrF;
    public User(File userFile) throws IOException, ParseException {
        this.usrF = userFile;
        prp = new Properties();
        prp.loadFromXML(Files.newInputStream(userFile.toPath()));
        this.type = UserType.fromString(prp.getProperty("TYPE"));
        this.username = prp.getProperty("USER");
        this.name = prp.getProperty("NAME");
        this.surname = prp.getProperty("SURNAME");
        this.birth = SimpleDateFormat.getDateInstance().parse(prp.getProperty("BIRTH"));
        this.address = Address.fromString(prp.getProperty("ADDRESS"));
        this.registration = SimpleDateFormat.getTimeInstance().parse(prp.getProperty("REGISTRATION"));
        this.expiration = SimpleDateFormat.getTimeInstance().parse(prp.getProperty("EXPIRATION"));
    }
    public void save() throws IOException {
        prp.storeToXML(Files.newOutputStream(usrF.toPath()), "Created by maramLib");
    }
    public User(String username, char[] password, UserType type, File path) throws UserFileException{
        usrF = new File(path, username + ".user");
        try{
            usrF.createNewFile();
        }catch (Exception e){
            throw new UserFileException("User file is not modifiable, changes will not be saved");
        }
        this.prp = new Properties();
        this.username = username;
        this.password = password;
        this.type = type;
        this.registration = Date.from(Instant.ofEpochMilli(System.currentTimeMillis()));
        prp.setProperty("USER", username);
        prp.setProperty("TYPE", type.toString());
        prp.setProperty("REGISTRATION", registration.toString());
    }
}
