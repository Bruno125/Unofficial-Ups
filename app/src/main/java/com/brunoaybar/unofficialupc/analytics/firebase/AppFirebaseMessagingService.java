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

    private void handleData(Map<String, String> data){
        if(data == null && data.isEmpty())
            return;

        String downloadUrl = data.get(KEY_DOWNLOAD_URL);
        if(downloadUrl != null){
            openMainWithDownloadUrl(downloadUrl);
            return;
        }

    }

    private static final String KEY_DOWNLOAD_URL = "download_url";

    void openMainWithDownloadUrl(String url){
        Intent i = new Intent();
        i.setClass(getApplicationContext(),MainActivity.class);
        i.setAction(MainActivity.class.getName());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtras(getDownloadBundle(url));
        getApplicationContext().startActivity(i);
    }

    Bundle getDownloadBundle(String downloadUrl){
        Bundle bundle = new Bundle();
        bundle.putString( MainActivity.PARAM_DONWLOAD_URL,downloadUrl);
        return bundle;
    }

}
