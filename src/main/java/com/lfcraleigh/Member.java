package com.lfcraleigh;

public class Member {

    String firstName;
    String lastName;
    String email;
    boolean currentDues = false;
    boolean joinMailingList;


    public Member() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isCurrentDues() {
        return currentDues;
    }

    public void setCurrentDues(boolean currentDues) {
        this.currentDues = currentDues;
    }
}
