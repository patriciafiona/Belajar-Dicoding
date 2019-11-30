package com.path_studio.submission3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ErrorHandling {

    public void error_alert(Context mContext, String title, String error){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(error)
                .setTitle(title);
        // Add the buttons
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
