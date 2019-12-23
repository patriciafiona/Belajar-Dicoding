package com.path_studio.submission_05.Alarm_Reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.path_studio.submission_05.BuildConfig;
import com.path_studio.submission_05.R;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import cz.msebera.android.httpclient.Header;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TYPE_DAILY = "Daily Reminder";
    public static final String TYPE_RELEASE = "RELEASE Reminder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    // Siapkan 2 id untuk 2 macam alarm, daily dan release
    private final int ID_DAILY = 100;
    private final int ID_RELEASE = 101;

    private final static String TIME_FORMAT = "HH:mm";

    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.e("Masuk on Recieve","Masuk");
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = type.equalsIgnoreCase(TYPE_DAILY) ? TYPE_DAILY : TYPE_RELEASE;
        int notifId = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;

        showToast(context, title, message);

        //memunculkan notifikasi
        if(notifId == ID_RELEASE){
            Log.e("Masuk proses API", "MAsuk");
            //call data from API

            //dapatkan tanggal hari ini
            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(c);

            AsyncHttpClient client = new AsyncHttpClient();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_date.gte="+today+"&primary_release_date.lte="+today;
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    try {
                        JSONObject responseObject = new JSONObject(result);

                        int totalResult = responseObject.getInt("total_results");
                        for (int i = 0; i < totalResult; i++) {
                            String movie_title = responseObject.getJSONArray("results").getJSONObject(i).getString("title");
                            String message = movie_title + " " + context.getResources().getString(R.string.has_been_release);

                            showAlarmNotification(context, movie_title, message, ID_RELEASE+i);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Exception API", e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("onFailure", error.toString());
                }
            });
        }else{
            showAlarmNotification(context, title, message, notifId);
        }

    }

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    public void setRepeatingAlarmDaily(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, context.getResources().getString(R.string.alarm_set_up), Toast.LENGTH_SHORT).show();
    }

    public void setRepeatingAlarmRelease(Context context, String type, String time){
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        Toast.makeText(context, context.getResources().getString(R.string.alarm_set_up), Toast.LENGTH_SHORT).show();

    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, context.getResources().getString(R.string.alarm_cancle), Toast.LENGTH_SHORT).show();
    }

    public boolean checkStatusDailyAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);

        boolean status_daily = (PendingIntent.getBroadcast(context, ID_DAILY, intent, PendingIntent.FLAG_NO_CREATE) != null);
        return status_daily;
    }

    public boolean checkStatusReleaseAlarm(Context context){
        Intent intent = new Intent(context, AlarmReceiver.class);

        boolean status_release = (PendingIntent.getBroadcast(context, ID_RELEASE, intent, PendingIntent.FLAG_NO_CREATE) != null);
        return status_release;
    }


}
