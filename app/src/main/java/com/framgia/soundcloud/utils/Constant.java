package com.framgia.soundcloud.utils;

import com.framgia.soundcloud.BuildConfig;
import com.framgia.soundcloud.R;

/**
 * Created by anh on 19/10/2017.
 */

public class Constant {
    public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String BASE_URL = "http://api.soundcloud.com";
    public static final int COLUMN_RECYCLER_VIEW = 2;
    public static final String CATEGORY_BUNDLE = "category";
    public static final String NUMBER_LINKED_PARTITIONING = "1";
    public static final String PAGE_SIZE = "10";
    public static final String POSTION_BUNDLE = "postion";
    public static final int DEFAULT_VALUE_INT = -1;
    public static final String BROADCAST_UPDATE_CONTROL = "com.soundclound_02.UpdateControl";
    public static final String BROADCAST_CURRENT_POSTION = "currentpostion";
    public static final String ACTION_UPDATE_SEEK_BAR =
            "com.soundcloud_02" + ".ACTION_UPDATE_SEEK_BAR";
    public static final String BUFFERING_LEVEL = "bufferinglevel";
    public static final String STREAM_URL =
            "/stream?" + Constant.Param.CLIENT_ID + "=" + BuildConfig.API_KEY;
    public static final int SEEKBAR_DELAY_TIME = 1000;
    public static final String SIZE_IMAGE = "t500x500";
    public static final String LARGE = "large";
    public static final String BROADCAST_IS_PLAYING = "isplaying";
    public static int[] IMAGE_CATEGORY = {
            R.drawable.ic_all, R.drawable.ic_audio, R.drawable.ic_ambient, R.drawable.ic_classical,
            R.drawable.ic_country, R.drawable.ic_pop, R.drawable.ic_hiphop_rap,
            R.drawable.ic_dance_edm, R.drawable.ic_altinaterock, R.drawable.ic_deep_house,
    };

    private Constant() {
    }

    public class Param {
        public static final String CLIENT_ID = "client_id";
        public static final String GENRES = "genres";
        public static final String LINKED_PARTITIONING = "linked_partitioning";
        public static final String OFFSET = "offset";
    }
}
