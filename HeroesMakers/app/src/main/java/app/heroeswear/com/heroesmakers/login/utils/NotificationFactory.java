package app.heroeswear.com.heroesmakers.login.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import app.heroeswear.com.heroesmakers.R;
import app.heroeswear.com.heroesmakers.login.enums.NotificationChannelType;

public class NotificationFactory {

    public static final int NOTIFICATION_ID = 1111;

    @RequiresApi(Build.VERSION_CODES.O)
    public static void build(Context context,
                             String title,
                             String body,
                             NotificationChannelType channelType) {

//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra(EXTRA_RING_REMINDER_TYPE, mRingReminderType.getType());
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelType.getId())
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(Notification.PRIORITY_HIGH);
        //.setContentIntent(pendingIntent);
        notify(context, NOTIFICATION_ID, builder);
    }

    private static void notify(Context context, int id, NotificationCompat.Builder builder) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(id, builder.build());
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public static void createChannel(Context context, NotificationChannelType channelType) {
        NotificationChannel channel = new NotificationChannel(channelType.getId(),
                channelType.getName(),
                channelType.getImportance());
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}
