package com.example.noor.fypv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("child_email")
    @Expose
    private String child_email;
    @SerializedName("handle")
    @Expose
    private String handle;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("verified_status")
    @Expose
    private int verified_status;

    public int getVerified_status() {
        return verified_status;
    }

    public Child(String fullname, String child_email, String handle, String age) {
        this.fullname = fullname;
        this.child_email = child_email;
        this.handle = handle;
        this.age = age;
    }

    public String getFullname() {
        return fullname;
    }

    public String getChild_email() {
        return child_email;
    }

    public String getHandle() {
        return handle;
    }

    public String getAge() {
        return age;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setChild_email(String child_email) {
        this.child_email = child_email;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
