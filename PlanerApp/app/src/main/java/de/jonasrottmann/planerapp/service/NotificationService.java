package de.jonasrottmann.planerapp.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import de.jonasrottmann.planerapp.R;
import de.jonasrottmann.planerapp.data.Course;
import de.jonasrottmann.planerapp.ui.MainActivity;

/**
 * Created by Jonas Rottmann on 27.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class NotificationService extends Service {

    private static final String EXTRA_COURSE_ID = "EXTRA_COURSE_ID";

    public static Intent createIntent(@NonNull Context context, @NonNull Course course) {
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra(EXTRA_COURSE_ID, course);
        return i;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getParcelableExtra(EXTRA_COURSE_ID) == null) {
            throw new IllegalArgumentException("No course found in intent extras!");
        }

        Course course = intent.getParcelableExtra(EXTRA_COURSE_ID);

        // Create PendingIntent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, MainActivity.createIntent(this, course.getId()), PendingIntent.FLAG_UPDATE_CURRENT);
        // Prepare notification
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle(String.format("%s startet gleich.", course.getName())).setContentIntent(resultPendingIntent);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it
        mNotifyMgr.notify(course.getId(), mBuilder.build());

        return START_REDELIVER_INTENT;
    }
}
