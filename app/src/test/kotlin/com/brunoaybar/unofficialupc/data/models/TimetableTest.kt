package com.brunoaybar.unofficialupc.data.models

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class TimetableTest{

    private val DATE = SimpleDateFormat("yyyyMMdd",Locale.US).parse("20170202")
    private lateinit var timetable : Timetable

    @Before
    fun setUp(){
        timetable = Timetable()
    }

    @Test
    fun shouldSaveDay(){
        val day = Timetable.Day()

        day.date = DATE
        timetable.addDay(day)

        timetable.getDay(DATE) shouldEqual day
    }

}