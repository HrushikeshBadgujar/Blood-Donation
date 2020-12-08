package com.example.blooddonation;

public class DemoModel {


     String City, Name ;

    public DemoModel() {
    }

    public DemoModel(String city, String name) {
        City = city;
        Name = name;
    }


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
