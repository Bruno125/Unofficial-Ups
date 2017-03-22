package com.brunoaybar.unofficialupc.data.source.remote

import com.brunoaybar.unofficialupc.data.source.remote.responses.ReserveAvailabilityResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ReserveAvailabilityResponseTest{

    private lateinit var response: ReserveAvailabilityResponse
    private val JSON_SUCCESS = "{\"CodError\": \"00000\"," +
            "\"MsgError\": \"\"," +
            "\"TipoRecurso\": \"CO\"," +
            "\"FecReserva\": \"24032017 0800\"," +
            "\"CanHoras\": \"1\"," +
            "\"Recursos\": [" +
            "{\"CodRecurso\": 34,\"NomRecurso\": \"COMPUTADORA IMAC 04 (SALA 1)\",\"Local\": \"CAMPUS MONTERRICO\",\"FecReserva\": \"24032017\",\"HoraIni\": \"0800\",\"HoraFin\": \"0900\",\"Estado\": false,\"CodError\": null,\"Mensaje\": null}," +
            "{\"CodRecurso\": 35,\"NomRecurso\": \"COMPUTADORA IMAC 05 (SALA 1)\",\"Local\": \"CAMPUS MONTERRICO\",\"FecReserva\": \"24032017\",\"HoraIni\": \"0800\",\"HoraFin\": \"0900\",\"Estado\": false,\"CodError\": null,\"Mensaje\": null}]}"

    @Test
    fun transformSuccess(){
        response = getResponse(JSON_SUCCESS)
        assertFalse { response.isError }

        val result = response.transform()

        result.size shouldEqual 2
        result[0].code shouldEqual "34"
        result[0].name shouldEqual "COMPUTADORA IMAC 04 (SALA 1)"
        result[0].venue shouldEqual "CAMPUS MONTERRICO"
        result[0].duration shouldEqual 1

        val calendar = Calendar.getInstance()
        calendar.time = result[0].datetime
        calendar.get(Calendar.DAY_OF_MONTH) shouldEqual 24
        calendar.get(Calendar.MONTH) shouldEqual 2 // march is month 2 because count starts in 0
        calendar.get(Calendar.YEAR) shouldEqual 2017
        calendar.get(Calendar.HOUR) shouldEqual 8

    }

    private val JSON_ERROR = "{\"CodError\": \"00002\"" +
            ",\"MsgError\": \"No hay recursos disponibles para reservar.\"," +
            "\"TipoRecurso\": null,\"FecReserva\": null,\"CanHoras\": null,\"Recursos\": null}"

    @Test
    fun transformError(){
        response = getResponse(JSON_ERROR)
        assertTrue { response.isError }
    }

    private fun getResponse(json: String): ReserveAvailabilityResponse{
        val type = object : TypeToken<ReserveAvailabilityResponse>() {}.type
        return Gson().fromJson<ReserveAvailabilityResponse>(json,type)
    }
}