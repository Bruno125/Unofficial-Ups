package com.brunoaybar.unofficialupc.data.source.remote

import com.brunoaybar.unofficialupc.data.source.remote.responses.AbsencesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldEqual
import org.junit.Test
import kotlin.test.assertFalse

class AbsencesResponseTest {

    private lateinit var response : AbsencesResponse
    private val JSON_SUCCESS =
            "{\"CodError\":\"00000\", " +
            "\"MsgError\":\"\", " +
            "\"Inasistencias\":[ " +
            "{\"CodCurso\":\"MA263\",\"CodInasistencia\":1,\"CodAlumno\":\"U201412074\",\"CursoNombre\":\"Cálculo II\",\"Maximo\":\"7\",\"Total\":\"2\"}, " +
            "{\"CodCurso\":\"CI119\",\"CodInasistencia\":4,\"CodAlumno\":\"U201412074\",\"CursoNombre\":\"Estática\",\"Maximo\":\"6\",\"Total\":\"0\"}]}"

    @Test
    fun transformSuccess() {
        response = getResponse(JSON_SUCCESS)
        assertFalse { response.isError }

        val result = response.transform()
        result.size shouldEqual 2
        result[0].code shouldEqual "1"
        result[0].courseCode shouldEqual "MA263"
        result[0].courseName shouldEqual "Cálculo II"
        result[0].maximum shouldEqual 7
        result[0].total shouldEqual 2

        result[1].code shouldEqual "4"
        result[1].courseCode shouldEqual "CI119"
        result[1].courseName shouldEqual "Estática"
        result[1].maximum shouldEqual 6
        result[1].total shouldEqual 0
    }


    private fun getResponse(jsonString: String) : AbsencesResponse{
        val type = object : TypeToken<AbsencesResponse>() {}.type
        return Gson().fromJson<AbsencesResponse>(jsonString, type)
    }


}

