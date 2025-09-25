package it.maram.auth;

public enum GenericUserType implements UserType{
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
    public static GenericUserType fromString(String s){
        switch (s){
            case "USER": return USER;
            case "ADMIN": return ADMIN;
            case "DEVELOPER": return DEVELOPER;
            default: return null;
        }
    }
    public int get() {
        switch (this) {
            case USER:
                return 2;
            case ADMIN:
                return 1;
            case DEVELOPER:
                return 0;
            default:
                return -1;
        }
    }
}
