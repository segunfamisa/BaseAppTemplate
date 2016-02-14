package com.segunfamisa.base.ui.fragments.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Window;


import com.segunfamisa.base.R;
import com.segunfamisa.base.ui.activities.BaseActivity;
import com.segunfamisa.base.ui.fragments.BaseFragment;
import com.segunfamisa.base.utils.BitmapUtils;
import com.segunfamisa.base.utils.Config;
import com.segunfamisa.base.utils.DialogUtils;
import com.segunfamisa.base.utils.Logger;
import com.segunfamisa.base.utils.UriUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Media Picker dialog
 *
 * Created by segunfamisa on 9/9/15.
 */
public class MediaPickerDialog extends BaseDialogFragment {
    /**
     * String for log tags
     */
    private static String TAG = "MediaPicker";

    /**
     * Directory for Truppr
     */
    public static final String DIR_NAME = Config.APPNAME;

    /**
     * Flag for Image media type
     */
    public static final int TYPE_IMAGE = 0;

    /**
     * Flag for Video media type
     */
    public static final int TYPE_VIDEO = 1;

    /**
     * Flag for Audio media type
     */
    public static final int TYPE_AUDIO = 2;

    /**
     * Flag for All media types
     */
    public static final int TYPE_ALL = 10;

    /**
     * Duration for video recording
     */
    public static final int MAX_DURATION_VIDEO = 15;

    /**
     * variable to hold the media type
     */
    private int mMediaType = -1;
    private String mTitle;



    /**
     * Listener for picked media
     */
    private MediaPickedListener mMediaPickedListener;

    /**
     * Intent Request code for recording image
     */
    private final static int REQ_CAPTURE_IMAGE = 100;

    /**
     * Intent request code for recording video
     */
    private final static int REQ_CAPTURE_VIDEO = 200;

    /**
     * Intent request code for picking image
     */
    private final static int REQ_PICK_IMAGE = 300;

    /**
     * Intent request code for picking video
     */
    private final static int REQ_PICK_VIDEO = 400;

    /**
     * Key for saving instance state of media type
     */
    private static final String KEY_MEDIATYPE = "key_media_type";

    /**
     * Key for saving instance state of the listener
     */
    private static final String KEY_LISTENER = "key_media_picked_listener";

    /**
     * Key for saving instance state for title of the dialog
     */
    private static final String KEY_TITLE = "key_title";

    /**
     * Create an instance of the fragment
     * @param title
     * @param mediaType
     * @return
     */
    public static DialogFragment newInstance(String title, int mediaType){
        return newInstance(title, mediaType, null);
    }

    /**
     * Create an instance of the fragment
     * @param title
     * @param mediaType
     * @param mediaPickedListener
     * @return
     */
    public static DialogFragment newInstance(String title, int mediaType, MediaPickedListener mediaPickedListener){
        MediaPickerDialog frag = new MediaPickerDialog();
        frag.mMediaType = mediaType;
        frag.mTitle = title;
        frag.mMediaPickedListener = mediaPickedListener;
        return frag;
    }

    public MediaPickedListener getMediaPickedListener(){
        return mMediaPickedListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(KEY_MEDIATYPE)){
                mMediaType = savedInstanceState.getInt(KEY_MEDIATYPE);
            }
            if(savedInstanceState.containsKey(KEY_TITLE)){
                mTitle = savedInstanceState.getString(KEY_TITLE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_MEDIATYPE, mMediaType);
        outState.putString(KEY_TITLE, mTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mMediaPickedListener = (MediaPickerDialog.MediaPickedListener)getActivity();
        }
        catch(ClassCastException cce){
            throw new ClassCastException("The activity must implement " + MediaPickerDialog.MediaPickedListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if(TextUtils.isEmpty(mTitle)){
            //set window to have no title
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        else{
            dialog.setTitle(mTitle);
        }

        //create alert dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        //set title
        if(!TextUtils.isEmpty(mTitle))
            mBuilder.setTitle(mTitle);


        //items array res
        int itemsRes = -1;

        //select items array based on type
        switch(mMediaType){
            case TYPE_AUDIO:
                itemsRes = R.array.items_dialog_media_audio;
                break;
            case TYPE_VIDEO:
                itemsRes = R.array.items_dialog_media_video;
                break;
            case TYPE_IMAGE:
                itemsRes = R.array.items_dialog_media_image;
            default:
                break;
        }

        if(itemsRes != -1){
            //set items && click listener
            mBuilder.setItems(itemsRes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch(i){
                        case 0:
                            //launch fragment that handles media actions
                            //0 defaults to creating a new media
                            startPickerFragment(mMediaType, MediaPickerFragment.ACTION_NEW, mMediaPickedListener);
                            break;
                        case 1:
                            //1 choosing exisiting media
                            startPickerFragment(mMediaType, MediaPickerFragment.ACTION_PICK, mMediaPickedListener);
                            break;
                    }
                }
            });

            //create dialog
            dialog = mBuilder.create();
        }

        return dialog;
    }


    private void startPickerFragment(int mediaType, int action, MediaPickedListener mediaPickedListener){
        Fragment frag = MediaPickerFragment.newInstance(mediaType, action);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.container, frag, frag.getClass().getSimpleName());
        ft.addToBackStack(null);
        ft.commit();
    }


    public interface MediaPickedListener extends Serializable {
        public void onMediaItemPicked(Uri fileUri, int mediaType);
        public void onCancel(String message);
    }

    public static class MediaPickerFragment extends BaseFragment {

        /**
         * Action for new media item
         */
        public static final int ACTION_NEW = 0;

        /**
         * Action for picking existing media item
         */
        public static final int ACTION_PICK = 1;

        /**
         * Variable for holding the required action
         */
        public int mAction = -1;

        /**
         * Uri object for medai
         */
        private Uri mMediaUri;

        /**
         * Listener for picked media
         */
        private MediaPickedListener mMediaPickedListener;

        /**
         * variable to hold the media type
         */
        private int mMediaType = -1;

        private static final int REQUEST_PERMISSION_STORAGE = 100;

        private static final int REQUEST_PERMISSION_CAMERA = 200;

        /**
         * Get instance of the picker fragment
         * @param mediaType
         * @param action
         * @return
         */
        public static Fragment newInstance(int mediaType, int action){
            MediaPickerFragment frag = new MediaPickerFragment();
            frag.mMediaType = mediaType;
            frag.mAction = action;
            return frag;
        }



        /** Create a file Uri for saving an image or video
         *
         */
        private static Uri getOutputMediaFileUri(int type){
            File output = getOutputMediaFile(type);
            if(output != null)
                return Uri.fromFile(output);

            return null;
        }


        /**
         * Create a File for saving an image or video
         */
        private static File getOutputMediaFile(int type){
            //check storage state
            if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)){
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), DIR_NAME);
                // Create the storage directory if it does not exist
                if (! mediaStorageDir.exists()){
                    boolean created = mediaStorageDir.mkdirs();
                    boolean isDirectory = mediaStorageDir.isDirectory();

                    if (! (created || isDirectory)){

                        return null;
                    }
                }

                // Create a media file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File mediaFile;
                if (type == TYPE_IMAGE){
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                            "IMG_"+ timeStamp + ".jpg");
                } else if(type == TYPE_VIDEO) {
                    mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                            "VID_"+ timeStamp + ".mp4");
                } else {
                    return null;
                }

                return mediaFile;
            }

            return null;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(requestCode == REQUEST_PERMISSION_STORAGE) {
                //for the storage permission request
                //check if the grantResults array is not empty and it contains PackageManager.PERMISSION_GRANTED
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    createNewMedia(mMediaType);
                } else{
                    //permission has been denied. Disable the feature.
                }
            } else if(requestCode == REQUEST_PERMISSION_CAMERA) {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    createNewMedia(mMediaType);
                } else{
                    //permission has been denied. Disable the feature.
                }
            } else{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }

        private void createNewMedia(int mediaType){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    DialogUtils.createSimpleOkDialog(getContext(), getString(R.string.title_dialog_storage_permission),
                            getString(R.string.title_dialog_storage_permission), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showInstalledAppDetails(getContext());
                                }
                            }).show();
                } else {
                    //request for permission. You can listen for a response via onRequestPermissionsResult method.
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
                }
            } else if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    DialogUtils.createSimpleOkDialog(getContext(), getString(R.string.title_dialog_camera_permission),
                            getString(R.string.message_dialog_camera_permission), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showInstalledAppDetails(getContext());
                                }
                            }).show();
                } else {
                    //request for permission. You can listen for a response via onRequestPermissionsResult method.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
                }
            } else {
                Intent intent;

                switch (mediaType) {
                    case TYPE_IMAGE:
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mMediaUri = getOutputMediaFileUri(TYPE_IMAGE);
                        if (mMediaUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                            startActivityForResult(intent, REQ_CAPTURE_IMAGE);
                        } else {
                            //if listener isn't empty, send back error
                            if (mMediaPickedListener != null) {
                                mMediaPickedListener.onCancel(getString(R.string.picker_error_image));
                            }
                        }
                        break;

                    case TYPE_VIDEO:
                        intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        mMediaUri = getOutputMediaFileUri(TYPE_VIDEO);
                        if (mMediaUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, MAX_DURATION_VIDEO);
                            startActivityForResult(intent, REQ_CAPTURE_VIDEO);
                        } else {
                            //if listener isn't empty, send back error
                            if (mMediaPickedListener != null) {
                                mMediaPickedListener.onCancel(getString(R.string.picker_error_video));
                            }
                        }
                        break;

                    default:

                        break;
                }
            }

        }

        /**
         * Method to launch the app info settings page
         * @param context
         */
        public static void showInstalledAppDetails(Context context) {
            if (context == null) {
                return;
            }
            final Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);

        }

        private void choseExistingMedia(int mediaType){
            Intent intent = null;

            switch (mediaType){
                case TYPE_IMAGE:
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");

                    startActivityForResult(Intent.createChooser(intent, getString(R.string.picker_title_image)), REQ_PICK_IMAGE);
                    break;

                case TYPE_VIDEO:
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("video/*, images/*");

                    startActivityForResult(Intent.createChooser(intent, getString(R.string.picker_title_image)), REQ_PICK_IMAGE);
                    break;
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //start the picking action
            switch (mAction){
                case ACTION_NEW:
                    createNewMedia(mMediaType);
                    break;
                case ACTION_PICK:
                    choseExistingMedia(mMediaType);
                    break;
            }
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            try{
                mMediaPickedListener = (MediaPickerDialog.MediaPickedListener)getActivity();
            }
            catch(ClassCastException cce){
                throw new ClassCastException("The activity must implement " + MediaPickerDialog.MediaPickedListener.class.getSimpleName());
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            //retrieve data
            if(resultCode == BaseActivity.RESULT_OK){
                //for user capture requests
                if(requestCode == REQ_CAPTURE_IMAGE || requestCode == REQ_CAPTURE_VIDEO){
                    if(mMediaType == TYPE_IMAGE) {
                        scaleImage(mMediaUri);
                    } else {
                        mMediaPickedListener.onMediaItemPicked(mMediaUri, mMediaType);
                        closeFragment();
                    }
                }

                if(requestCode == REQ_PICK_IMAGE || requestCode == REQ_PICK_VIDEO){
                    mMediaUri = Uri.fromFile(new File(UriUtils.getPath(getContext(), data.getData())));

                    if(mMediaPickedListener != null && mMediaUri != null){
                        if(mMediaType == TYPE_IMAGE) {
                            scaleImage(mMediaUri);
                        } else {
                            mMediaPickedListener.onMediaItemPicked(mMediaUri, mMediaType);
                            closeFragment();
                        }
                    }
                }
            }
            else if(resultCode == BaseActivity.RESULT_CANCELED){
                //user cancelled
                closeFragment();
            }
        }

        private void scaleImage(Uri imageUri) {
            Bitmap bmp = BitmapUtils.decodeBitmapFromStream(getActivity(), imageUri, 512, 512);
            if(bmp != null) {
                try {
                    File file = new File(imageUri.getPath());
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);

                    mMediaPickedListener.onMediaItemPicked(Uri.fromFile(file), mMediaType);

                    closeFragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
