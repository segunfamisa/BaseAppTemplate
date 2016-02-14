package com.segunfamisa.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.InputStream;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
public class BitmapUtils {

    /**
     * To decode and scale a bitmap from URI stream
     * @param context context
     * @param uri uri containing the image
     * @param reqWidth required width
     * @param reqHeight required height
     * @return the decoded {@link Bitmap} if successful or null if it failed
     */
    public static Bitmap decodeBitmapFromStream(Context context, Uri uri, int reqWidth, int reqHeight) {
        try {
            InputStream input = context.getContentResolver().openInputStream(uri);

            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);

            if(input != null) {
                input.close();
            }

            if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
                return null;

            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = calculateInSampleSize(onlyBoundsOptions, reqWidth, reqHeight);
            bitmapOptions.inDither=true;//optional
            bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
            input = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);

            if(input != null) {
                input.close();
            }

            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
