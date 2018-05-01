package common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;


import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import app.heroeswear.com.heroesmakers.login.models.User;


public class AppSettingsProfile {

    private static final String TAG = "AppSettingsProfile";

    private static final String SETTINGS_FILE_NAME = "app_pref.dat";

    private static final String IMMUTABLE_SETTINGS_FILE_NAME = "immutable_app_pref.dat";

    private static final String PREF_TIME_ON_EXIT = "PREF_TIME_ON_EXIT";

    private static AppSettingsProfile _instance = new AppSettingsProfile();

    private static final ReentrantLock settingsLock = new ReentrantLock();



    public static AppSettingsProfile getInstance() {
        return _instance;
    }

    public SharedPreferences getPrefsInstance() {
        return get.getContext().getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getImmutablePrefsInstance() {
        return GetTaxiApplication.getContext().getSharedPreferences(IMMUTABLE_SETTINGS_FILE_NAME, Context.MODE_PRIVATE);
    }

//    public void clearAll() {
//        SharedPreferences prefs = getPrefsInstance();
//        prefs.edit().clear().apply();
//        GetTaxiApplication.getContext().deleteFile(SETTINGS_FILE_NAME);
//        _instance = new AppSettingsProfile();
//    }

    public boolean isFirstStart() {
        return getPrefsInstance().getBoolean("FIRST_LAUNCH", false);
    }

    public void setFirstStart(boolean value) {
        SharedPreferences.Editor editor = getPrefsInstance().edit();
        editor.putBoolean("FIRST_LAUNCH", value);
        editor.apply();
    }

    public float getDefaultTips() {
        return getPrefsInstance().getFloat("TIPS", -1);
    }

    public void setDefaultTips(float tips) {
        SharedPreferences.Editor editor = getPrefsInstance().edit();
        editor.putFloat("TIPS", tips);
        editor.apply();
    }

    public String getPackageName() {
        return GetTaxiApplication.getContext().getPackageName();
    }




    public String getDefaultCreditCard() {
        return getPrefsInstance().getString("DEFAULT_CARD", null);
    }

    public boolean isPlayStoreRated() {
        return getPrefsInstance().getBoolean("PLAY_STORE_RATE", false);
    }

    public void setPlayStoreAsRated(User user) {
        SharedPreferences.Editor editor = getPrefsInstance().edit();
        editor.put("USER_PROFILE", user);
        editor.apply();
    }

    public void saveSettings() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                settingsLock.lock();
                try {
                    Log.d(TAG, "Save settings");
                    SharedPreferences.Editor editor = getPrefsInstance().edit();
                    String serializedSettings = ObjectSerializer.objectToString(Settings.getInstance());
                    editor.putString("SETTINGS_GSON", serializedSettings);
                    editor.apply();
                } finally {
                    settingsLock.unlock();
                }
            }
        }).start();
    }

    public void restoreSettings(Settings settingsRestorTo) {
        String raw_settings = getPrefsInstance().getString("SETTINGS_GSON", "");
        if (!TextUtils.isEmpty(raw_settings)) {
            long start_time = new Date().getTime();
            Settings deserializedSettings = (Settings) ObjectSerializer.stringToObject(raw_settings,
                    Settings.class);
            if (deserializedSettings != null) {
                settingsRestorTo.merge(deserializedSettings);
                settingsRestorTo.save();
            }

            Logger.d("SettingsRestore", "Restore settings taked " + (new Date().getTime() - start_time) / 1000 + " sec");
        }


        /**
         * User notification preferences
         * OTHER = 0;
         * SMS = 1;
         * EMAIL = 2;
         * PUSH = 4;
         * @return
         */

    public void changeUserNotificationPreferencesToken(int np) {
        SharedPreferences.Editor editor = getPrefsInstance().edit();
        editor.putInt("NOTIFICATIONS_PREFERENCES", np);
        editor.apply();
    }

    public int getUserNotificationPreferences() {
        return getPrefsInstance().getInt("NOTIFICATIONS_PREFERENCES", PUSH);
    }

}
