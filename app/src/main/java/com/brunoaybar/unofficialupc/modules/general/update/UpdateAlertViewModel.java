package com.brunoaybar.unofficialupc.modules.general.update;

import android.content.Context;

import com.brunoaybar.unofficialupc.UpcApplication;
import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;
import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.utils.interfaces.AndroidUtils;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by fanlat on 29/03/17.
 */

public class UpdateAlertViewModel {

    @Inject
    public Context context;

    @Inject
    public AppRemoteConfig appRemoteConfig;

    @Inject
    public AndroidUtils androidUtils;

    public UpdateAlertViewModel(){
        UpcApplication.getViewModelsComponent().inject(this);
    }

    public Observable<Boolean> shouldDisplay(){
        int currentVersionCode = androidUtils.getVersionCode();
        return appRemoteConfig.getLatestVersion().map( v -> currentVersionCode < v);
    }

    public Observable<String> getDownloadUrl(){
        return appRemoteConfig.getDownloadUrl();
    }


    public void updated(){
        appRemoteConfig.getLatestVersion()
                .subscribe( latestVersion -> {
                    int currentVersionCode = androidUtils.getVersionCode();
                    AnalyticsManager.eventUpdated(context, currentVersionCode,latestVersion);
                },e -> {});
    }

}
