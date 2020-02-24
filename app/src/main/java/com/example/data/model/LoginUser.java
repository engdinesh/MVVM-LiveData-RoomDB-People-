package com.example.data.model;

import android.util.Patterns;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginUser {


    @SerializedName("email") public String strEmailAddress;
    @SerializedName("password") public String strPassword;

    public LoginUser(String EmailAddress, String Password) {
        strEmailAddress = EmailAddress;
        strPassword = Password;
    }

    public String getStrEmailAddress() {
        return strEmailAddress;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getStrEmailAddress()).matches();
    }


    public boolean isPasswordLengthGreaterThan5() {
        return getStrPassword().length() > 5;
    }

}

