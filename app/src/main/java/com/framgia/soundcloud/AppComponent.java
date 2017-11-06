package com.framgia.soundcloud;

import android.content.Context;
import com.framgia.soundcloud.data.source.local.provider.ContentResolverTrack;
import com.framgia.soundcloud.data.source.local.sharedpref.SharedPrefsImplement;
import com.framgia.soundcloud.data.source.remote.service.NetworkModule;
import com.framgia.soundcloud.data.source.remote.service.TrackApi;
import com.framgia.soundcloud.utils.dagger.AppScope;
import dagger.Component;

/**
 * Created by anh on 26/10/2017.
 */

@AppScope
@Component(modules = { AppModule.class, NetworkModule.class })
public interface AppComponent {
    ContentResolverTrack contentResolverTrack();

    SharedPrefsImplement sharedPrefsImplement();

    TrackApi trackApi();

    Context applicationContext();
}
