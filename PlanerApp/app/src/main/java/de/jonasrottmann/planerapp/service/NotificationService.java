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
import de.jonasrottmann.planerapp.ui.MainActivity;

/**
 * Created by Jonas Rottmann on 27.01.17.
 * Copyright © 2017 fluidmobile. All rights reserved.
 */
public class NotificationService extends Service {

    private static final String EXTRA_COURSE_ID = "EXTRA_COURSE_ID";

    public static Intent createIntent(@NonNull Context context, int courseId) {
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra(EXTRA_COURSE_ID, courseId);
        return i;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra(EXTRA_COURSE_ID, -1) == -1) {
            throw new IllegalArgumentException("No course id found in intent extras!");
        }

        int courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);

        // Create PendingIntent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, MainActivity.createIntent(this, courseId), PendingIntent.FLAG_UPDATE_CURRENT);
        // Prepare notification
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("My notification").setContentText("Hello World!").setContentIntent(resultPendingIntent);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it
        mNotifyMgr.notify(courseId, mBuilder.build());

        return super.onStartCommand(intent, flags, startId);
    }
}
