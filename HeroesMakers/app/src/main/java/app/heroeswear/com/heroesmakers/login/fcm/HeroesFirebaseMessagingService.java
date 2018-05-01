package app.heroeswear.com.heroesmakers.login.fcm;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import app.heroeswear.com.heroesfb.Logger;
import app.heroeswear.com.heroesmakers.login.Activities.AreYouOkActivity;
import app.heroeswear.com.heroesmakers.login.enums.NotificationChannelType;
import app.heroeswear.com.heroesmakers.login.utils.NotificationFactory;

public class HeroesFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        Logger.Companion.d("Title: " + remoteMessage.getNotification().getTitle());
//        NotificationFactory.build(this, notification.getTitle(), notification.getBody(), NotificationChannelType.HEART_RATE);

        Intent in = new Intent(this, AreYouOkActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }
}
