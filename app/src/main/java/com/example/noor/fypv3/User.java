package com.example.noor.fypv3;


import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    @SerializedName("response")
    private String response;

    @SerializedName("username")
    private String username;

    @SerializedName("data")
    private List<Child> children;


    public List<Child> getData() {
        return children;
    }

    public String getResponse() {
        return response;
    }

    @SerializedName("Status")
    private String Status;

    @SerializedName("tweet")
    private String tweet;

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return Status;
    }

    public String getTweet() {
        return tweet;
    }
}
