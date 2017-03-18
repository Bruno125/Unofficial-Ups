package com.brunoaybar.unofficialupc;

import android.app.Application;

import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;
import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.data.source.injection.DaggerDataComponent;
import com.brunoaybar.unofficialupc.data.source.injection.DataComponent;
import com.brunoaybar.unofficialupc.data.source.injection.DataModule;
import com.brunoaybar.unofficialupc.injection.AppComponent;
import com.brunoaybar.unofficialupc.injection.AppModule;
import com.brunoaybar.unofficialupc.injection.DaggerAppComponent;
import com.brunoaybar.unofficialupc.modules.DaggerViewModelsComponent;
import com.brunoaybar.unofficialupc.modules.ViewModelsComponent;
import com.brunoaybar.unofficialupc.data.source.injection.RepositoryModule;

/**
 * Created by brunoaybar on 21/10/2016.
 */

public class UpcApplication extends Application {

    private static UpcApplication INSTANCE;
    public static UpcApplication get(){
        return INSTANCE;
    }

    private AppComponent component;
    private DataComponent dataComponent;
    private ViewModelsComponent viewModelsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        AppRemoteConfig.setup();
        AnalyticsManager.setup(this);

        setupDaggerComponents();
    }

    private void setupDaggerComponents(){
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        dataComponent = DaggerDataComponent.builder().dataModule(new DataModule()).build();
        viewModelsComponent = DaggerViewModelsComponent.builder().repositoryModule(new RepositoryModule()).build();
    }

    public static AppComponent getComponent(){
        return get().component;
    }

    public static DataComponent getDataComponent(){
        return get().dataComponent;
    }

    public static ViewModelsComponent getViewModelsComponent() {
        return get().viewModelsComponent;
    }


}
