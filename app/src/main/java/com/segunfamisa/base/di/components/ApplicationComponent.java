package com.segunfamisa.base.di.components;

import android.content.Context;

import com.segunfamisa.base.di.modules.ApplicationModule;
import com.segunfamisa.base.navigator.Navigator;
import com.segunfamisa.base.ui.base.BaseActivity;
import com.segunfamisa.base.ui.base.BaseFragment;
import com.segunfamisa.base.utils.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    Context context();
    PreferenceUtils preferenceUtils();
    Navigator navigator();
}
