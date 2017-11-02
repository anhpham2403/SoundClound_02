package com.framgia.soundcloud.screen.home.offline;

import com.framgia.soundcloud.AppComponent;
import com.framgia.soundcloud.utils.dagger.FragmentScope;
import dagger.Component;

/**
 * Created by anh on 02/11/2017.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = OfflineModule.class)
public interface OfflineComponent {
    void inject(OfflineFragment offlineFragment);
}
