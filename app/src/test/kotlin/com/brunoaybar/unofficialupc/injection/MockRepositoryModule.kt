package com.brunoaybar.unofficialupc.injection

import com.brunoaybar.unofficialupc.data.repository.LoginRepository
import com.brunoaybar.unofficialupc.data.repository.SessionRepository
import com.brunoaybar.unofficialupc.data.repository.UserRepository
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockRepositoryModule{

    @Provides @Singleton
    fun provideSessionRepo(): SessionRepository = mock<SessionRepository>()
    @Provides @Singleton
    fun provideLoginRepo(): LoginRepository = mock<LoginRepository>()
    @Provides @Singleton
    fun provideUserRepo(): UserRepository = mock<UserRepository>()
}