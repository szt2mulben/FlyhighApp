package hu.bence.flyhigh.model;

import com.google.gson.annotations.SerializedName;

public class LoginTokenResponse {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
