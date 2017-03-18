package com.brunoaybar.unofficialupc.injection

import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig
import com.brunoaybar.unofficialupc.data.source.injection.DataModule
import com.brunoaybar.unofficialupc.data.source.interfaces.ApplicationDao
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource
import com.brunoaybar.unofficialupc.utils.interfaces.DateProvider
import com.brunoaybar.unofficialupc.utils.interfaces.InternetVerifier
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockDataModule {

    @Provides @Singleton
    fun providesDao(): ApplicationDao = mock<ApplicationDao>()

    @Provides @Singleton
    fun providesRemoteSource(): RemoteSource = mock<RemoteSource>()

    @Provides @Singleton
    fun providesInternetVerifier(): InternetVerifier = mock<InternetVerifier>()

    @Provides @Singleton
    fun providesRemoteConfig(): AppRemoteConfig = mock<AppRemoteConfig>()

}