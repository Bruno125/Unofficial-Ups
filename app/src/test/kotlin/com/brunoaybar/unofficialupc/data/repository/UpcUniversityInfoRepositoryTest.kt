package com.brunoaybar.unofficialupc.data.repository


import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig
import com.brunoaybar.unofficialupc.data.models.ReserveFilter
import com.brunoaybar.unofficialupc.data.repository.impl.UpcUniversityInfoRepository
import com.brunoaybar.unofficialupc.data.source.interfaces.ApplicationDao
import com.brunoaybar.unofficialupc.data.source.interfaces.RemoteSource
import com.brunoaybar.unofficialupc.testUtils.getDataComponent
import com.nhaarman.mockito_kotlin.whenever
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.observers.TestSubscriber
import javax.inject.Inject

//TODO: write more tests
class UpcUniversityInfoRepositoryTest{
    @Inject
    lateinit var mockAppDao: ApplicationDao
    @Inject
    lateinit var mockRemoteSource: RemoteSource
    @Inject
    lateinit var mockSessionRepo: SessionRepository
    @Inject
    lateinit var mockRemoteConfig: AppRemoteConfig

    private lateinit var repository: UpcUniversityInfoRepository

    @Before
    fun setUp(){
        val dataComponent = getDataComponent()
        repository = UpcUniversityInfoRepository(dataComponent)
        dataComponent.inject(this)
    }


    @Test
    fun shouldReturnSingleNotCustomFilterWithNoValues(){
        val singleNotCustomFilterJson = "[{\"key\":\"test\",\"service_key\":\"test_key\",\"custom\": false,\"name\": \"Test filter\",\"values\":[]} ]"
        whenever(mockRemoteConfig.reserveInfo).then { Observable.just(singleNotCustomFilterJson) }

        val testSubscriber = TestSubscriber<List<ReserveFilter>>()
        repository.reserveFilters.subscribe(testSubscriber)

        val expectedFilter = ReserveFilter("test","test_key",false,"Test filter", arrayListOf())
        val expectedList = listOf<ReserveFilter>(expectedFilter)

        testSubscriber.assertValue(expectedList)
    }

    //Does the same as above. I don't know which approach is better.
    //Downside of the one above is that we depend on the "equals" implementation of the tested value
    //"Downside" of the one below is that it's more verbose
    @Test
    fun shouldReturnSingleNotCustomFilterWithNoValues2(){
        val singleNotCustomFilterJson = "[{\"key\":\"test\",\"service_key\":\"test_key\",\"custom\": false,\"name\": \"Test filter\",\"values\":[]} ]"
        whenever(mockRemoteConfig.reserveInfo).then { Observable.just(singleNotCustomFilterJson) }

        val testSubscriber = TestSubscriber<List<ReserveFilter>>()
        repository.reserveFilters.subscribe(testSubscriber)

        val actualResult = testSubscriber.onNextEvents.first()
        actualResult.size shouldEqual 1

        actualResult.forEach {
            it.key shouldEqual "test"
            it.serviceKey shouldEqual "test_key"
            it.isCustom shouldEqual false
            it.name shouldEqual "Test filter"
            it.values shouldEqual arrayListOf()
        }

    }



}