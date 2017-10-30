package com.framgia.soundcloud.screen.listtracks;

import com.framgia.soundcloud.AppComponent;
import com.framgia.soundcloud.utils.dagger.ActivityScope;
import dagger.Component;

/**
 * Created by anh on 25/10/2017.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ListtracksModule.class)
public interface ListtracksComponent {
    void inject(ListtracksActivity listtracksActivity);
}
