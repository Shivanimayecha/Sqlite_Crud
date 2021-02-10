package com.example.shivaniprac_hardyinfotech.Model;

public class PatientModel {

    String id, name, dob, weight, image, email, deiseas, contact, gender;

    public PatientModel(String id, String name, String dob, String weight, String image, String email, String deiseas, String contact, String gender) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.weight = weight;
        this.image = image;
        this.email = email;
        this.deiseas = deiseas;
        this.contact = contact;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeiseas() {
        return deiseas;
    }

    public void setDeiseas(String deiseas) {
        this.deiseas = deiseas;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
