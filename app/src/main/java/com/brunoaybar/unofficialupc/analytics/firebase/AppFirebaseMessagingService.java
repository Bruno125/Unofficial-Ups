package com.brunoaybar.unofficialupc.analytics.firebase;

import android.content.Intent;
import android.os.Bundle;

import com.brunoaybar.unofficialupc.modules.general.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        handleData(remoteMessage.getData());
    }

    private static final String KEY_RESTART_APP = "restart_app";
    private void handleData(Map<String, String> data){
        if(data == null && data.isEmpty())
            return;

        boolean shouldRestart = data.get(KEY_RESTART_APP) != null;
        if(shouldRestart){
            openMain();
            return;
        }
    }

    void openMain(){
        Intent i = new Intent();
        i.setClass(getApplicationContext(),MainActivity.class);
        i.setAction(MainActivity.class.getName());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        getApplicationContext().startActivity(i);
    }
}
