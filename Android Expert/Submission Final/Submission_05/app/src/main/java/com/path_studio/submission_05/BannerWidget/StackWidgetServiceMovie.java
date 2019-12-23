package com.path_studio.submission_05.BannerWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetServiceMovie extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactoryMovie(this.getApplicationContext());
    }
}
