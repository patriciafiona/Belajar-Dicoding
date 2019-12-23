package com.path_studio.submission_05.BannerWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.path_studio.submission_05.R;

public class FavouriteTvBannerWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.path_studio.submission_05.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.path_studio.submission_05.EXTRA_ITEM";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetServiceTv.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favourite_tv_banner_widget);
        views.setRemoteAdapter(R.id.stack_view_tv, intent);
        views.setEmptyView(R.id.stack_view_tv, R.id.empty_view);

        Intent toastIntent = new Intent(context, FavouriteTvBannerWidget.class);
        toastIntent.setAction(FavouriteTvBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view_tv, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

