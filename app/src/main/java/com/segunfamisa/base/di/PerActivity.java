package com.segunfamisa.base.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Scope to indicate a module is bound by the lifecycle of an activity
 *
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
