package com.path_studio.submission3;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnectionCheck {

    public boolean isNetworkConnected(Context mcontext) {
        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
