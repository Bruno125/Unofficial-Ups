package com.brunoaybar.unofficialupc.testUtils

import rx.Observable
import rx.observers.TestSubscriber

fun <T> assertObservable(observable: Observable<T>, expected: T?){
    val subscriber = TestSubscriber<T>()
    observable.subscribe(subscriber)
    subscriber.assertValue(expected)
}
