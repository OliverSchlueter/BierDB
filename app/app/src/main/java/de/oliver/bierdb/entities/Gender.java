package de.oliver.bierdb.entities;

public enum Gender {

    MALE("Männlich"),
    FEMALE("Weiblich"),
    NO_GENDER("NO_GENDER");

    private String german;
    Gender(String german){
        this.german = german;
    }

    public String getGerman() {
        return german;
    }

    public static Gender parse(String s){
        for (Gender gender : values()) {
            if(gender.getGerman().equalsIgnoreCase(s)){
                return gender;
            }
        }

        return null;
    }
}
