package com.segunfamisa.base.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.segunfamisa.base.R;

/**
 * Class for creating simple dialogs
 *
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
public class DialogUtils {

    /**
     * Creates a simple ok dialog
     *
     * @param context context
     * @param title title
     * @param message message
     * @return {@link AlertDialog}
     */
    public static Dialog createSimpleOkDialog(Context context, String title, String message) {
        return createSimpleOkDialog(context, title, message, null);
    }

    /**
     * Creates a simple ok dialog
     *
     * @param context context
     * @param title title
     * @param message message
     * @param okClickListener click listener
     *
     * @return {@link AlertDialog}
     */
    public static Dialog createSimpleOkDialog(Context context, String title, String message,
                                              DialogInterface.OnClickListener okClickListener) {
        return createSimpleOkDialog(context, title, message,
                context.getString(R.string.dialog_action_ok), okClickListener);
    }

    /**
     *
     * @param context context
     * @param title title
     * @param message message
     * @param okButtonText text to display on the ok button
     * @param okClickListener click listener
     *
     * @return {@link AlertDialog}
     */
    public static Dialog createSimpleOkDialog(Context context, String title, String message,
                          String okButtonText, DialogInterface.OnClickListener okClickListener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(okButtonText, okClickListener);
        return alertDialog.create();
    }

    /**
     * Creates a progress dialog with
     *
     * @param context activity context
     * @param message
     * @return
     */
    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        return progressDialog;
    }
}
