package com.brunoaybar.unofficialupc.data.source.remote

import com.brunoaybar.unofficialupc.data.source.remote.responses.LoginResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldEqual
import org.junit.Test

class LoginResponseTest{

    //region Demo JSON responses
    private val JSON_SUCCESS =
            "{ " +
            "\"Codigo\": \"U201313295\", " +
            "\"CodigoAlumno\": \"201313295\", " +
            "\"Nombres\": \"MIGUEL BRUNO\", " +
            "\"Apellidos\": \"AYBAR GUERRERO\", " +
            "\"Genero\": \"MASCULINO\", " +
            "\"EsAlumno\": \"ALU\", " +
            "\"Estado\": \"A\", " +
            "\"TipoUser\": \"ALUMNO\", " +
            "\"Token\": \"849363cb9c9a4cb1822e59b6cd95f9ab20161013044630\", " +
            "\"Datos\": { " +
            "\"CodLinea\": \"U\", " +
            "\"DscLinea\": \"Universidad Peruana de Ciencias Aplicadas\", " +
            "\"CodModal\": \"AC\", " +
            "\"DscModal\": \"PREGRADO-UPC\", " +
            "\"CodSede\": \"null\", " +
            "\"DscSede\": \"null\", " +
            "\"Ciclo\": \"201602\" " +
            "}, " +
            "\"CodError\": \"00000\", " +
            "\"MsgError\": \"\" " +
            "} "
    //endregion

    @Test
    fun transformSuccess(){
        val response = getResponse(JSON_SUCCESS)
        response.isError shouldEqual false

        val result = response.transform()
        result.userCode shouldEqual "U201313295"
        result.names shouldEqual "MIGUEL BRUNO"
        result.lastnames shouldEqual "AYBAR GUERRERO"
        result.token shouldEqual "849363cb9c9a4cb1822e59b6cd95f9ab20161013044630"
        result.genre shouldEqual "MASCULINO"
    }

    private fun getResponse(jsonString: String) : LoginResponse {
        val type = object : TypeToken<LoginResponse>() {}.type
        return Gson().fromJson<LoginResponse>(jsonString, type)
    }

}
