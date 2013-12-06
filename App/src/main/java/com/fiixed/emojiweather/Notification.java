package com.fiixed.emojiweather;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

/**
 * Created by abell on 12/6/13.
 */
public class Notification {
    Context context;

    public Notification(Context context) {
        this.context = context;
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.weather);

        RemoteViews customNotifView = new RemoteViews(context.getPackageName(),
                R.layout.custom_notification);
        customNotifView.setTextViewText(R.id.text, "Its raining!");
        customNotifView.setImageViewResource(R.id.imageView, R.drawable.weather);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.weather)
                        .setLargeIcon(bm)
                        .setContentTitle("My notification")
//                            .setContentInfo("5")

                        .setContentText("Hello World!");

        mBuilder.setContent(customNotifView);

//            NotificationCompat.InboxStyle inboxStyle =
//                    new NotificationCompat.InboxStyle();
//            String[] events = new String[6];
//            events[0] = "die die die";
//            events[1] = "hey hey hey";
//            events[2] = "die die die";
//            events[3] = "hey hey hey";
//            events[4] = "die die die";
//            events[5] = "hey hey hey";
//            // Sets a title for the Inbox style big view
//            inboxStyle.setBigContentTitle("Event tracker details:");
//
//            // Moves events into the big view
//            for (int i=0; i < events.length; i++) {
//
//                inboxStyle.addLine(events[i]);
//            }
//            // Moves the big view style object into the notification object.
//            mBuilder.setStyle(inboxStyle);

        // Issue the notification here.

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int mId = 1;
        mNotificationManager.notify(mId, mBuilder.build());

    }



}
