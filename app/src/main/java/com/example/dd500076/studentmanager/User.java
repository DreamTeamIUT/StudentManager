package com.example.dd500076.studentmanager;

/**
 * Created by dd500076 on 27/02/17.
 */

public class User {
    public String name;
    public String idEtu;
    public String surname;
    public String studies;
    public int year;

    public User(String idEtu, String name, String surname, String studies, int year) {
        this.name = name;
        this.surname = surname;
        this.studies = studies;
        this.idEtu = idEtu;
        this.year = year;
    }

    public String getIdEtu() {
        return idEtu;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStudies() {
        return studies;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return name;
    }
}
