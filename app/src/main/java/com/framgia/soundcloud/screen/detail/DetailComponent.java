package com.framgia.soundcloud.screen.detail;

import com.framgia.soundcloud.AppComponent;
import com.framgia.soundcloud.utils.dagger.ActivityScope;
import dagger.Component;

/**
 * Created by anh on 28/10/2017.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = DetailModule.class)
public interface DetailComponent {
    void inject(DetailActivity detailActivity);
}