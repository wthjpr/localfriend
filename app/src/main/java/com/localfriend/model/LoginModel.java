package com.localfriend.model;

import java.io.Serializable;

/**
 * Created by User on 12/27/2016.
 */
public class LoginModel implements Serializable {
    private static final long serialVersionUID = 7562289865747L;

    public String username;
    public String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
