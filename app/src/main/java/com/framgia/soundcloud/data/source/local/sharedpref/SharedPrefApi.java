package com.framgia.soundcloud.data.source.local.sharedpref;

/**
 * Created by anh on 05/11/2017.
 */

public interface SharedPrefApi {
    <T> T get(String key, Class<T> clazz);
    <T> void put(String key, T data);
    void clear();
}
