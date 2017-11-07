package com.framgia.soundcloud.screen.player;

import com.framgia.soundcloud.AppComponent;
import com.framgia.soundcloud.utils.dagger.ActivityScope;
import dagger.Component;

/**
 * Created by anh on 08/11/2017.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = PlayerModule.class)
public interface PlayerComponent {
    void inject(PlayerFragment playerFragment);
}
