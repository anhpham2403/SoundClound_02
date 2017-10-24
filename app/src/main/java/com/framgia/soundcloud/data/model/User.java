package com.framgia.soundcloud.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anh on 23/10/2017.
 */

public class User {
    @SerializedName("full_name")
    private String mFullName;
    @SerializedName("id")
    private int mId;
    @SerializedName("username")
    private String mUserName;
    @SerializedName("avatar_url")
    private String mAvatarUrl;

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }
}
