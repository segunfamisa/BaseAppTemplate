package com.segunfamisa.base.utils;

import java.util.Collection;

/**
 * Created by Segun Famisa {segunfamisa@gmail.com} on 9/17/15.
 */
public class ListUtils {

    public static boolean isEmpty(Collection items){
        return items == null || items.size() < 1;
    }
}
