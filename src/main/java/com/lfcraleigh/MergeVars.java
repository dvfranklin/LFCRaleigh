package com.lfcraleigh;


import com.ecwid.mailchimp.MailChimpObject;

public class MergeVars extends MailChimpObject {

    @Field
    public String EMAIL, FNAME, LNAME;

    public MergeVars() {
    }

    public MergeVars(String email, String fname, String lname) {
        this.EMAIL = email;
        this.FNAME = fname;
        this.LNAME = lname;
    }
}
