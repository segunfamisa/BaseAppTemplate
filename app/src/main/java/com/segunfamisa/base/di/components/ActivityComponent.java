package com.segunfamisa.base.di.components;

import android.app.Activity;

import com.segunfamisa.base.di.PerActivity;
import com.segunfamisa.base.di.modules.ActivityModule;

import dagger.Component;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //exposed to sub-graph
    Activity activity();
}
