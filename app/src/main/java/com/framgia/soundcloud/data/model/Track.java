package com.framgia.soundcloud.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.framgia.soundcloud.utils.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anh on 23/10/2017.
 */

public class Track extends BaseObservable implements Parcelable {
    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
    @SerializedName("artwork_url")
    private String mArtworkUrl;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("downloadable")
    private boolean mDownloadable;
    @SerializedName("duration")
    private int mDuration;
    @SerializedName("id")
    private int mId;
    @SerializedName("uri")
    private String mUri;
    @SerializedName("playback_count")
    private double mPlaybackCount;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("user")
    private User mUser;
    @SerializedName("download_url")
    private String mDownloadUrl;

    public Track() {
    }

    protected Track(Parcel in) {
        mArtworkUrl = in.readString();
        mCreatedAt = in.readString();
        mDescription = in.readString();
        mDownloadable = in.readByte() != 0;
        mDuration = in.readInt();
        mId = in.readInt();
        mUri = in.readString();
        mPlaybackCount = in.readDouble();
        mTitle = in.readString();
        mDownloadUrl = in.readString();
        mUser = in.readParcelable(User.class.getClassLoader());
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getDownloadWithClientId() {
        return mDownloadUrl + Constant.CLIENT_ID;
    }

    @Bindable
    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    @Bindable
    public String getArtworkUrlWithBigSize() {
        return mArtworkUrl != null ? mArtworkUrl.replace(Constant.LARGE, Constant.SIZE_IMAGE)
                : null;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public double getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(double playbackCount) {
        mPlaybackCount = playbackCount;
    }

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Bindable
    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtworkUrl);
        dest.writeString(mCreatedAt);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeInt(mDuration);
        dest.writeInt(mId);
        dest.writeString(mUri);
        dest.writeDouble(mPlaybackCount);
        dest.writeString(mTitle);
        dest.writeString(mDownloadUrl);
        dest.writeParcelable(mUser, flags);
    }

    public String getFullUri() {
        return getUri() + Constant.STREAM_URL;
    }
}
