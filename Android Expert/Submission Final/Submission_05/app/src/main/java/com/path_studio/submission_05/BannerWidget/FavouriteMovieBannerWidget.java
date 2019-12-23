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

public class FavouriteMovieBannerWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.path_studio.submission_05.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.path_studio.submission_05.EXTRA_ITEM";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetServiceMovie.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favourite_movie_banner_widget);
        views.setRemoteAdapter(R.id.stack_view_movie, intent);
        views.setEmptyView(R.id.stack_view_movie, R.id.empty_view);

        Intent toastIntent = new Intent(context, FavouriteMovieBannerWidget.class);
        toastIntent.setAction(FavouriteMovieBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view_movie, toastPendingIntent);

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

