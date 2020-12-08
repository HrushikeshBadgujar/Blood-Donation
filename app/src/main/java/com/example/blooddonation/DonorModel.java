package com.example.blooddonation;

public class DonorModel {

    String Age, Blood_Grp, City, Full_Name, Locality, Phone;

    public DonorModel() {
    }

    public DonorModel(String age, String blood_Grp, String city, String full_Name, String locality, String phone) {
        Age = age;
        Blood_Grp = blood_Grp;
        City = city;
        Full_Name = full_Name;
        Locality = locality;
        Phone = phone;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBlood_Grp() {
        return Blood_Grp;
    }

    public void setBlood_Grp(String blood_Grp) {
        Blood_Grp = blood_Grp;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getLocality() {
        return Locality;
    }

    public void setLocality(String locality) {
        Locality = locality;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


}
