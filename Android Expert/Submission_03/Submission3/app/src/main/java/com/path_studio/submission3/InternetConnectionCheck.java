package com.path_studio.submission3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;

public class InternetConnectionCheck {

    public boolean isNetworkConnected(Context mcontext) {
        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void showAlertDialog(final Context mcontext){
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        mcontext.startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        System.exit(0);
                    }
                });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
