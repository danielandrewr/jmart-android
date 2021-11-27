package com.josephusdanieljmartfa.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Store extends Serializable {

    public static final String REGEX_PHONE = "(^[0-9]{9,12}$)";
    public static final String REGEX_NAME = "(^[A-Z][a-z0-9][\\S\\s]{4,20}$)";
    public String name;
    public String address;
    public String phoneNumber;

    public Store(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

//    @Override
//    public boolean read(String content) {
//        return false;
//    }

    public String toString() {
        return "name: " + this.name + "\n" + "address: " + this.address + "\n" + "phoneNumber: " + this.phoneNumber + "\n";
    }

    public boolean validate() {
        Pattern p = Pattern.compile(REGEX_NAME);
        Pattern p1 = Pattern.compile(REGEX_PHONE);
        Matcher m1 = p.matcher(this.name);
        Matcher m2 = p1.matcher(this.phoneNumber);
        if (m1.find() == true && m2.find() == true) {
            return true;
        }
        return false;
    }
}
