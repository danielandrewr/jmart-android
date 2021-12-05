package com.josephusdanieljmartfa.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Serializable {

    public static final String REGEX_EMAIL = "^[a-zA-Z0-9_&_*~]+(?:\\.[a-zA-Z0-9_&_*~]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9-]+)*$";
    public static final String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[a-zA-Z0-9]{8,}$";
    public String name;
    public String email;
    public String password;
    public double balance;
    public Store store;
    public String getId() {
        return String.valueOf(id);
    }

    public Account(String name, String email, String password, double balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.store = null;
    }

    public String toString() {
        return "name: " + this.name + "\n" + "email: " + this.email + "\n" + "password: " + this.password;
    }

    public boolean validate() {
        Pattern p1 = Pattern.compile(REGEX_EMAIL);
        Pattern p2 = Pattern.compile(REGEX_PASSWORD);
        Matcher m1 = p1.matcher(this.email);
        Matcher m2 = p2.matcher(this.password);
        if (m1.find() == true && m2.find() == true) {
            return true;
        }
        return false;
    }
}
