/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.sql.Blob;

/**
 *
 * @author Nevets
 */
public class Employee {

    int userId, pincode;
    String name, email, address, extras, contact, age, gender, skills, bio;
    Blob[] PARcard;
    Blob Certificate;
    Blob profileImage;

    public Employee() {

    }

    public Employee(int userId, String contact, int pincode, String name, String email, String address, String extras, Blob[] PARcard, Blob Certificate, Blob profileImage) {
        this.userId = userId;
        this.contact = contact;
        this.pincode = pincode;
        this.name = name;
        this.email = email;
        this.address = address;
        this.extras = extras;
        this.PARcard = PARcard;
        this.Certificate = Certificate;
        this.profileImage = profileImage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public Blob[] getPARcard() {
        return PARcard;
    }

    public void setPARcard(Blob[] PARcard) {
        this.PARcard = PARcard;
    }

    public Blob getCertificate() {
        return Certificate;
    }

    public void setCertificate(Blob Certificate) {
        this.Certificate = Certificate;
    }

    public Blob getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Blob profileImage) {
        this.profileImage = profileImage;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
