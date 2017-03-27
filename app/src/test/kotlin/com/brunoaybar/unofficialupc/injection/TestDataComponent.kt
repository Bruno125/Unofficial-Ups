package com.brunoaybar.unofficialupc.injection

import com.brunoaybar.unofficialupc.data.repository.UpcLoginRepositoryTest
import com.brunoaybar.unofficialupc.data.repository.UpcUniversityInfoRepositoryTest
import com.brunoaybar.unofficialupc.data.source.injection.BaseDataComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf( MockDataModule::class, MockRepositoryModule::class))
interface TestDataComponent : BaseDataComponent{
    fun inject(target: UpcLoginRepositoryTest)
    fun inject(target: UpcUniversityInfoRepositoryTest)
}
