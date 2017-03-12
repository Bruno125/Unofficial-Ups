package com.brunoaybar.unofficialupc.data.source.remote

import com.brunoaybar.unofficialupc.data.source.remote.responses.ClassmatesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldEqual
import org.junit.Test
import kotlin.test.assertFalse

class ClassmatesResponseTest{

    private val JSON_SUCCESS =
            "{\"CodError\":\"00000\", " +
            "\"MsgError\":\"\", " +
            "\"cursoId\":\"MA263\", " +
            "\"curso\":\"CÁLCULO II\", " +
            "\"seccion\":\"IX31\", " +
            "\"alumnos\":[ " +
            "{\"nombre_completo\":\"ALIAGA ROJAS, KAREN JESSICA\",\"codigo\":\"U201417432\",\"url_foto\":\"http://intranet.upc.edu.pe/programas/IMAGEN/FOTOS/UPC/0540201417432.JPG\",\"carrera_actual\":\"INGENIERÍA CIVIL\"}, " +
            "{\"nombre_completo\":\"CALDERON SUAREZ, JEAN PIERRE\",\"codigo\":\"U201412806\",\"url_foto\":\"http://intranet.upc.edu.pe/programas/IMAGEN/FOTOS/UPC/0540201412806.JPG\",\"carrera_actual\":\"INGENIERÍA INDUSTRIAL\"}, " +
            "{\"nombre_completo\":\"CANALES CUBA, GRECIA THALIA MARYBEL\",\"codigo\":\"U201413845\",\"url_foto\":\"http://intranet.upc.edu.pe/programas/IMAGEN/FOTOS/UPC/0540201413845.JPG\",\"carrera_actual\":\"INGENIERÍA INDUSTRIAL\"}]} "


    @Test
    fun transformSuccess(){
        val response = getResponse(JSON_SUCCESS)
        assertFalse { response.isError }

        val result = response.transform()
        result.size shouldEqual 3

        result[0].name shouldEqual "ALIAGA ROJAS, KAREN JESSICA"
        result[0].code shouldEqual "U201417432"
        result[0].career shouldEqual "INGENIERÍA CIVIL"
        result[0].photo shouldEqual "http://intranet.upc.edu.pe/programas/IMAGEN/FOTOS/UPC/0540201417432.JPG"

        result[1].name shouldEqual "CALDERON SUAREZ, JEAN PIERRE"
        result[1].code shouldEqual "U201412806"
        result[1].career shouldEqual "INGENIERÍA INDUSTRIAL"
        result[1].photo shouldEqual "http://intranet.upc.edu.pe/programas/IMAGEN/FOTOS/UPC/0540201412806.JPG"

        result[2].name shouldEqual "CANALES CUBA, GRECIA THALIA MARYBEL"
        result[2].code shouldEqual "U201413845"
        result[2].career shouldEqual "INGENIERÍA INDUSTRIAL"
        result[2].photo shouldEqual "http://intranet.upc.edu.pe/programas/IMAGEN/FOTOS/UPC/0540201413845.JPG"
    }

    private fun getResponse(jsonString: String) : ClassmatesResponse {
        val type = object : TypeToken<ClassmatesResponse>() {}.type
        return Gson().fromJson<ClassmatesResponse>(jsonString, type)
    }

}
