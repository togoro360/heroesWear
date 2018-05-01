package app.heroeswear.com.heroesmakers.login.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import app.heroeswear.com.heroesfb.Logger;
import app.heroeswear.com.heroesmakers.login.enums.NotificationChannelType;
import app.heroeswear.com.heroesmakers.login.utils.NotificationFactory;

public class HeroesFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Logger.Companion.d("Title: " + remoteMessage.getNotification().getTitle());
        NotificationFactory.build(this, notification.getTitle(), notification.getBody(), NotificationChannelType.HEART_RATE);
    }
}
