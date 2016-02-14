package com.segunfamisa.base.di;

/**
 * Interface representing a contract for clients that contain components for dependency injection
 *
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
public interface HasComponent<C> {
    C getComponent();
}
