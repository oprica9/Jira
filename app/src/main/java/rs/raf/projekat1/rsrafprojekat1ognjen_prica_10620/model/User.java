package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.model.enums.Type;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private Type type;
    private boolean rememberMe = false;

    public User(int id, String username, String email, String password, Type type) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

