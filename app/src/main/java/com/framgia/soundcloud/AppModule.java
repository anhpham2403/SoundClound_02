package com.framgia.soundcloud;

import android.content.Context;
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
}
