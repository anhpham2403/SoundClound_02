package com.framgia.soundcloud.screen.home;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by anh on 23/10/2017.
 */

@Retention(SOURCE)
@IntDef({ TabMode.CATEGORY, TabMode.OFFLINE })
public @interface TabMode {
    int CATEGORY = 0;
    int OFFLINE = 1;
}

