package com.segunfamisa.base.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.segunfamisa.base.App;
import com.segunfamisa.base.di.components.ActivityComponent;
import com.segunfamisa.base.di.components.ApplicationComponent;
import com.segunfamisa.base.di.components.DaggerActivityComponent;
import com.segunfamisa.base.di.modules.ActivityModule;
import com.segunfamisa.base.navigator.Navigator;
import com.segunfamisa.base.utils.AppUtils;
import com.segunfamisa.base.utils.PreferenceUtils;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Inject
    PreferenceUtils mPrefs;

    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getApplicationComponent().inject(this);
        mPrefs = getApplicationComponent().preferenceUtils();
        mNavigator = getApplicationComponent().navigator();
    }

    /**
     * Gets the application component for dependency injection
     *
     * @return {@link ApplicationComponent}
     */
    private ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    /**
     * Gets the activity component for dependecy injection
     *
     * @return {@link ActivityComponent}
     */
    protected ActivityComponent getActivityComponent() {
        if(mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }

        return mActivityComponent;
    }

    /**
     * Gets the activity module for dependency injection
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * Adds a fragment to a container view
     *
     * @param containerViewId container view id
     * @param fragment fragment to add
     * @param addToBackStack flag whether to add to back stack or not
     */
    protected void addFragment(int containerViewId, Fragment fragment, boolean addToBackStack) {
        if(containerViewId > -1 && fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(containerViewId, fragment, fragment.getClass().getName());
            if(addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    /**
     * Replaces a fragment in a container view.
     *
     * @param containerViewId container view id
     * @param fragment fragment to replace
     * @param addToBackStack flag whether or not to add to backstack
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, boolean addToBackStack) {
        if(containerViewId > -1 && fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(containerViewId, fragment, fragment.getClass().getName());
            if(addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    /**
     * Closes the fragments embedded in this activity, if there are no fragments on the backstack,
     * it finishes the activity
     */
    private void close() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * Hides the keyboard
     */
    protected void hideKeyboard() {
        AppUtils.hideKeyboard(this);
    }
}
