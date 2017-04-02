package com.brunoaybar.unofficialupc.data.repository.impl.demo

import com.brunoaybar.unofficialupc.data.models.*
import com.brunoaybar.unofficialupc.data.repository.UserRepository
import com.brunoaybar.unofficialupc.utils.Rand
import rx.Observable
import java.util.*

class DemoUserRepository : UserRepository{

    override fun getTimeTable(): Observable<Timetable> {
        val timetable = Timetable()
        val date = Date()
        val calendar = Calendar.getInstance()
        for(i in 0..3){
            val day = Timetable.Day()
            day.date = date
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, Rand.int(7,19))
            day.classes.add(Timetable.Class("AA1","Curso 1","N1",calendar.time,"SI","AX01","A201",Rand.int(2,4)))
            timetable.addDay(day)
            calendar.add(Calendar.DAY_OF_MONTH,1)
        }
        return Observable.just(timetable)
    }

    override fun getCourses(): Observable<MutableList<Course>> {
        val courses = arrayListOf<Course>()
        val course = Course()
        course.code = "AA1"
        course.name = "Curso 1"
        course.formula = "PC1 *10% + EB*90%"
        course.currentProgress = 0.1
        courses.add(course)
        return Observable.just(courses)
    }

    override fun getCourseDetail(courseCode: String?): Observable<Course> {
        val course = Course()
        course.code = "AA1"
        course.name = "Curso 1"
        course.formula = "PC1 *10% + EB*90%"
        course.currentProgress = 0.1
        course.assesments = arrayListOf(
                Assessment("01","PRACTICA CALIFICADA","PC1","0",10.0,"15"),
                Assessment("02","EXAMEN FINAL","EB","0",90.0,"0")
        )
        return Observable.just(course)
    }

    override fun getAbsences(): Observable<MutableList<Absence>> {
        val absences = arrayListOf<Absence>(
                Absence("01","AA1","Curso 1",3,10)
        )
        return Observable.just(absences)
    }

    override fun getClassmates(courseCode: String?): Observable<MutableList<Classmate>> {
        val classmates = arrayListOf<Classmate>(
                Classmate("Bruno Aybar","u201313295","https://avatars0.githubusercontent.com/u/5692387?v=3&s=460","Ing. SW"),
                Classmate("Socrates","u000001","http://www.facts-about.org.uk/images/socrates.jpg","Filosofia"),
                Classmate("Raphael","u0001483","http://www.canvasreplicas.com/images/Raffaello%20Raphael%20Sanzio.jpg","Artes"),
                Classmate("Jason Mraz","u23061977","https://s3.amazonaws.com/bit-photos/large/6438274.jpeg","Música"),
                Classmate("John Cena","u23283239","https://thebenclark.files.wordpress.com/2014/03/facebook-default-no-profile-pic.jpg","Unknown")
        )
        return Observable.just(classmates)
    }

}