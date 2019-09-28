package com.example.noor.fypv3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseClass {
    @SerializedName("Status")
    private String Status;

    @SerializedName("Tweet")
    private String tweet;

    @SerializedName("response")
    private String response;

    @SerializedName("statusAll")
    private List<String> statusAll;

    public List<String> getStatusAll() {
        return statusAll;
    }

    public String getResponse() {
        return response;
    }

    public String getStatus() {
        return Status;
    }

    public String getTweet() {
        return tweet;
    }
}
