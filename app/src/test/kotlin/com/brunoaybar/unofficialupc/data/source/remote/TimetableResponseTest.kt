package com.brunoaybar.unofficialupc.data.source.remote

import com.brunoaybar.unofficialupc.data.source.remote.responses.TimetableResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertFalse

class TimetableResponseTest{

    //region Demo JSON responses
    private val JSON_SUCCESS =
            "{\"CodError\":\"00000\", " +
            "\"MsgError\":\"\", " +
            "\"HorarioDia\":[ " +
            "{\"CodDia\":1,\"CodAlumno\":\"U201412074\",\"Fecha\":\"20161010\", " +
            "\"Clases\":[ " +
            "{\"CodCurso\":\"MA263\", " +
            "\"CursoNombre\":\"Cálculo II\", " +
            "\"CursoNombreCorto\":\"CÁLCULO II\", " +
            "\"Fecha\":\"20161010\", " +
            "\"HoraInicio\":\"090000\", " +
            "\"HoraFin\":\"110000\", " +
            "\"Sede\":\"SI\", " +
            "\"Seccion\":\"IX31\", " +
            "\"Salon\":\"A-404\"}]}, " +
            "{\"CodDia\":3,\"CodAlumno\":\"U201412074\",\"Fecha\":\"20161012\", " +
            "\"Clases\":[ " +
            "{\"CodAlumno\":\"U201412074\", " +
            "\"CodCurso\":\"EF40\", " +
            "\"CursoNombre\":\"Economía para la Gestión (Ing)\", " +
            "\"CursoNombreCorto\":\"ECONOMIA PARA LA GESTION\", " +
            "\"Fecha\":\"20161012\", " +
            "\"HoraInicio\":\"070000\", " +
            "\"HoraFin\":\"090000\", " +
            "\"Sede\":\"SI\", " +
            "\"Seccion\":\"CX41\", " +
            "\"Salon\":\"A-603\"}, " +
            "{\"CodAlumno\":\"U201412074\", " +
            "\"CodCurso\":\"CI119\", " +
            "\"CursoNombre\":\"Estática\", " +
            "\"CursoNombreCorto\":\"ESTÁTICA\", " +
            "\"Fecha\":\"20161012\", " +
            "\"HoraInicio\":\"190000\", " +
            "\"HoraFin\":\"220000\", " +
            "\"Sede\":\"SI\", " +
            "\"Seccion\":\"CX43\", " +
            "\"Salon\":\"A-612\"}]}]} "

    private val JSON_NO_TIMETABLE =
            "{\"CodError\":\"00011\", " +
            "\"MsgError\":\"Ud. no tiene clases programadas para esta semana.\"," +
            "\"HorarioDia\":null} "
    //endregion

    @Test
    fun transformSuccess(){
        val response = getResponse(JSON_SUCCESS)
        assertFalse { response.isError }

        val result = response.transform()

        val date1 = getDate("20161010")
        val day1 =result.getDay(date1)
        day1.date shouldEqual date1
        day1.classes[0].courseCode shouldEqual "MA263"
        day1.classes[0].courseName shouldEqual "Cálculo II"
        day1.classes[0].room shouldEqual "A-404"
        day1.classes[0].venue shouldEqual "SI"
        day1.classes[0].section shouldEqual "IX31"
        day1.classes[0].date shouldEqual getDate("20161010","090000")
        day1.classes[0].duration shouldEqual 2

        val date2 = getDate("20161012")
        val day2 =result.getDay(date2)
        day2.date shouldEqual date2
        day2.classes[0].courseCode shouldEqual "EF40"
        day2.classes[0].courseName shouldEqual "Economía para la Gestión (Ing)"
        day2.classes[0].room shouldEqual "A-603"
        day2.classes[0].venue shouldEqual "SI"
        day2.classes[0].section shouldEqual "CX41"
        day2.classes[0].date shouldEqual getDate("20161012","070000")
        day2.classes[0].duration shouldEqual 2

        day2.classes[1].courseCode shouldEqual "CI119"
        day2.classes[1].courseName shouldEqual "Estática"
        day2.classes[1].room shouldEqual "A-612"
        day2.classes[1].venue shouldEqual "SI"
        day2.classes[1].section shouldEqual "CX43"
        day2.classes[1].date shouldEqual getDate("20161012","190000")
        day2.classes[1].duration shouldEqual 3

    }

    @Test
    fun failsWhenNotAvailable(){
        val response = getResponse(JSON_NO_TIMETABLE)
        assert(response.isError)
    }

    private fun getDate(date:String) : Date = SimpleDateFormat("yyyyMMdd",Locale.US).parse(date)

    private fun getDate(date:String, startTime:String) :Date = SimpleDateFormat("yyyyMMdd hhmmss", Locale.US).parse("${date} ${startTime}")

    private fun getResponse(jsonString: String) : TimetableResponse {
        val type = object : TypeToken<TimetableResponse>() {}.type
        return Gson().fromJson<TimetableResponse>(jsonString, type)
    }
}