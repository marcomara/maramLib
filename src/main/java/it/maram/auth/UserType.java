package it.maram.auth;

public interface UserType {
    String toString();
    static <T extends UserType> T fromString(String s){
        return null;
    }
    int get();
}
