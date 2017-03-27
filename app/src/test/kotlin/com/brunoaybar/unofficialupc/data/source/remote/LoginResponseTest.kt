package com.brunoaybar.unofficialupc.data.source.remote

import com.brunoaybar.unofficialupc.data.source.remote.responses.LoginResponse
import com.brunoaybar.unofficialupc.data.source.remote.responses.ServiceException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

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

    private val JSON_ERROR = "{\"Codigo\": null," +
            "\"CodigoAlumno\": null," +
            "\"Nombres\": null,\"Apellidos\": null,\"Genero\": null," +
            "\"EsAlumno\": null,\"Estado\": null,\"TipoUser\": null,\"Token\": null," +
            "\"Datos\": null,\"CodError\": \"00001\"," +
            "\"MsgError\": \"Usuario y/o contraseña incorrectos\"}"

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


    @Test
    fun responseIsError(){
        val response = getResponse(JSON_ERROR)
        assert(response.isError)
    }

    @Rule @JvmField
    public val exception = ExpectedException.none()
    @Test
    fun transformThrowsException_WhenCredentialsInvalid(){
        val response = getResponse(JSON_ERROR)
        exception.expect(ServiceException::class.java)
        exception.expectMessage("Usuario y/o contraseña incorrectos")

        response.transform()
    }

    private fun getResponse(jsonString: String) : LoginResponse {
        val type = object : TypeToken<LoginResponse>() {}.type
        return Gson().fromJson<LoginResponse>(jsonString, type)
    }

}
