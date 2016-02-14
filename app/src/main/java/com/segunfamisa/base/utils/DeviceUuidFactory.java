package com.segunfamisa.base.utils;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author segunfamisa
 *
 */
public class DeviceUuidFactory {
    protected volatile static UUID uuid;
    final static String ANDROID_2_2_ID = "9774d56d682e549c";
    PreferenceUtils prefs;

    public DeviceUuidFactory(Context context){
        if(uuid == null){
            synchronized (DeviceUuidFactory.class) {
                if(uuid == null){
                    prefs = PreferenceUtils.init(context);
                    final String id = prefs.getDeviceId();
                    if(id != null){
                        uuid = UUID.fromString(id);
                    }
                    else{
                        final String androidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
//						The above code returns a unique android id for all os versions apart from froyo
                        try{
                            if(!ANDROID_2_2_ID.equals(androidID)){
                                uuid = UUID.nameUUIDFromBytes(androidID.getBytes("utf-8"));
                            }
                            else{
                                final String deviceId =((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf-8")) : UUID.randomUUID();
                            }
                        }
                        catch(UnsupportedEncodingException e){
                            throw new RuntimeException(e);
                        }

                        prefs.setDeviceId(uuid.toString());
                    }
                }
            }
        }
    }

    public UUID getUUID(){
        return uuid;
    }

    public String getStringUUID(){
        return uuid.toString();
    }
}
