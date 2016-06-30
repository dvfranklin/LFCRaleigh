package com.lfcraleigh;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue
    int id;

    String firstName;
    String lastName;
    String email;
    boolean currentDues = false;
    boolean joinMailingList;
    boolean receivedSwag = false;


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

    public boolean isJoinMailingList() {
        return joinMailingList;
    }

    public void setJoinMailingList(boolean joinMailingList) {
        this.joinMailingList = joinMailingList;
    }

    public boolean isReceivedSwag() {
        return receivedSwag;
    }

    public void setReceivedSwag(boolean receivedSwag) {
        this.receivedSwag = receivedSwag;
    }
}
