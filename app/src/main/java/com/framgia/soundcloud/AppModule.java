package com.framgia.soundcloud;

import android.content.Context;
import com.framgia.soundcloud.data.source.local.provider.ContentResolverTrack;
import com.framgia.soundcloud.data.source.local.sharedpref.SharedPrefsImplement;
import com.framgia.soundcloud.utils.dagger.AppScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by anh on 26/10/2017.
 */
@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @AppScope
    @Provides
    public Context provideApplicationContext() {
        return mContext;
    }

    @Provides
    @AppScope
    public ContentResolverTrack provideContentResolverTrack() {
        return new ContentResolverTrack(mContext);
    }

    @Provides
    @AppScope
    public SharedPrefsImplement provideSharedPrefsImplement() {
        return new SharedPrefsImplement(mContext);
    }
}
