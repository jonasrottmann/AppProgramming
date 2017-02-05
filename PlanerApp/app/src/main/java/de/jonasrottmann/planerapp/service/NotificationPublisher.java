package de.jonasrottmann.planerapp.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import de.jonasrottmann.planerapp.BuildConfig;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.data.model.Course;
import de.jonasrottmann.planerapp.data.model.TimeSlot;
import de.jonasrottmann.planerapp.ui.MainActivity;
import timber.log.Timber;

/**
 * Created by Jonas Rottmann on 29.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class NotificationPublisher extends BroadcastReceiver {
    private static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    private static final String NOTIFICATION = "NOTIFICATION";

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        // Publish notification
        notificationManager.notify(id, notification);
        // Play sound
        context.startService(new Intent(context, NotificationSoundService.class));
    }

    public static void stopSound(Context context) {
        context.stopService(new Intent(context, NotificationSoundService.class));
    }

    public static void scheduleNotification(Context context, Course course) {
        Timber.d("scheduleNotification");
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long time = BuildConfig.DEBUG ? System.currentTimeMillis() + 5000 : course.getTimeslotInMillis() - (5 * (1000 * 60));
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, createNotificationBroadcastIntent(context, course));
    }

    public static void cancelScheduledNotification(Context context, Course course) {
        // Cancel alarm
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(createNotificationBroadcastIntent(context, course));
        // Cancel notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.cancel(course.getId());
    }

    private static PendingIntent createNotificationBroadcastIntent(Context context, Course course) {
        Intent intent = new Intent(context, NotificationPublisher.class);
        intent.putExtra(NotificationPublisher.NOTIFICATION_ID, course.getId());
        intent.putExtra(NotificationPublisher.NOTIFICATION, createNotification(context, course));
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Notification createNotification(Context context, Course course) {
        Intent contentIntent = MainActivity.createIntent(context, course.getId());
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(course.getName());
        builder.setContentText(String.format("Startet um %s", TimeSlot.getTimeSlotForId(course.getTimeslot())));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setOngoing(true); // Notification cannot be dismissed
        builder.setContentIntent(PendingIntent.getActivity(context, course.getId(), contentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        return builder.build();
    }
}