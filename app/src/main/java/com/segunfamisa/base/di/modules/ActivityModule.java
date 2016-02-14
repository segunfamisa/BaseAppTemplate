package com.segunfamisa.base.di.modules;

import android.app.Activity;
import android.content.Context;

import com.segunfamisa.base.di.ActivityContext;
import com.segunfamisa.base.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }
}
