package com.brunoaybar.unofficialupc.data.source.remote

import com.brunoaybar.unofficialupc.data.source.remote.responses.ClassmatesResponse
import com.brunoaybar.unofficialupc.data.source.remote.responses.CourseListResponse
import com.brunoaybar.unofficialupc.data.source.remote.responses.CourseResponse
import com.brunoaybar.unofficialupc.data.source.remote.responses.ServiceException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertFalse

class CourseResponseTest{

    //region Demo JSON responses
    private val JSON_COURSE_BASE =
            "{\"CodError\":\"00000\", " +
            "\"MsgError\":\"\", " +
            "\"CursoNombre\":\"Cálculo II\", " +
            "\"CodCurso\":\"MA263\", " +
            "\"Formula\":\"10% (PC1) + 20% (EA1) + 12% (PC2) + 13% (PC3) + 20% (TA1) + 25% (EB1)\", "

    private val JSON_COURSE_SUCCESS = JSON_COURSE_BASE +
            "\"PorcentajeAvance\":\"65%\", " +
            "\"NotaFinal\":\"6.5\", " +
            "\"Notas\":[ " +
            "{\"CodNota\":0,\"CodCurso\":\"MA263\",\"CodAlumno\":\"U201412074\",\"NombreEvaluacion\":\"EVALUACIÓN FINAL\",\"NombreCorto\":\"EB\",\"NroEvaluacion\":1,\"Peso\":\"65%\",\"Valor\":\"10\"}, " +
            "{\"CodNota\":0,\"CodCurso\":\"MA263\",\"CodAlumno\":\"U201412074\",\"NombreEvaluacion\":\"TAREAS ACADÉMICAS\",\"NombreCorto\":\"TA\",\"NroEvaluacion\":1,\"Peso\":\"35%\",\"Valor\":\"0\"}]} "

    private val JSON_COURSE_RETIRED = JSON_COURSE_BASE +
            "\"NotaFinal\":\"RET\"}"

    private val JSON_COURSE_LIST_SUCCESS =
            "{\"CodError\":\"00000\", " +
            "\"MsgError\":\"\", " +
            "\"Cursos\":[ " +
            "{\"CodCurso\":\"MA263\",\"CodAlumno\":\"U201412074\",\"CursoNombre\":\"CÁLCULO II\"}, " +
            "{\"CodCurso\":\"CI167\",\"CodAlumno\":\"U201412074\",\"CursoNombre\":\"MODELACIÓN DE EDIFICACIONES\"}]} "

    private val JSON_COURSE_ERROR = "{\"CodError\":\"00003\"," +
            "\"MsgError\":\"La sesión ha expirado o no se reconoce el usuario que solicita la operación.\"," +
            "\"CursoNombre\":null,\"CodCurso\":null,\"Formula\":null," +
            "\"PorcentajeAvance\":null,\"NotaFinal\":null,\"Notas\":null}"

    private val JSON_COURSE_LIST_ERROR = "{\"CodError\":\"00002\"," +
            "\"MsgError\":\"Ud. no se encuentra matriculado en el presente ciclo.\"," +
            "\"Cursos\":null}"

    //endregion

    @Test
    fun transformSingleSuccess(){
        val response = getCourseResponse(JSON_COURSE_SUCCESS)
        assertFalse { response.isError }

        val result = response.transform()
        result.code shouldEqual "MA263"
        result.name shouldEqual "Cálculo II"
        result.currentGrade shouldEqual 6.5
        result.currentProgress shouldEqual 65.0
        result.isValid shouldEqual true
        result.assesments.size shouldEqual 2

        result.assesments[0].grade shouldEqual "10"
        result.assesments[0].name shouldEqual "EVALUACIÓN FINAL"
        result.assesments[0].nickname shouldEqual "EB"
        result.assesments[0].weight shouldEqual 65.0

        result.assesments[1].grade shouldEqual "0"
        result.assesments[1].name shouldEqual "TAREAS ACADÉMICAS"
        result.assesments[1].nickname shouldEqual "TA"
        result.assesments[1].weight shouldEqual 35.0
    }

    @Test
    fun failsWhenRetired(){
        val response = getCourseResponse(JSON_COURSE_RETIRED)
        assertFalse { response.isError }

        val result = response.transform()
        assertFalse { result.isValid }
    }

    @Test
    fun transformListSuccess(){
        val response = getCourseListResponse(JSON_COURSE_LIST_SUCCESS)
        assertFalse { response.isError }

        val result = response.transform()
        result.size shouldEqual 2

        result[0].code shouldEqual "MA263"
        result[0].name shouldEqual "CÁLCULO II"
        assert(result[0].isValid)

        result[1].code shouldEqual "CI167"
        result[1].name shouldEqual "MODELACIÓN DE EDIFICACIONES"
        assert(result[1].isValid)
    }


    @Test
    fun responseSingleIsError(){
        val response = getCourseResponse(JSON_COURSE_ERROR)
        assert(response.isError)
    }

    @Test
    fun responseListIsError(){
        val response = getCourseResponse(JSON_COURSE_LIST_ERROR)
        assert(response.isError)
    }

    @Rule @JvmField
    public val exception = ExpectedException.none()
    @Test
    fun transformThrowsException_WhenSingleIsError(){
        val response = getCourseResponse(JSON_COURSE_ERROR)
        exception.expect(ServiceException::class.java)
        exception.expectMessage("La sesión ha expirado o no se reconoce el usuario que solicita la operación.")

        response.transform()
    }

    @Test
    fun transformThrowsException_WhenListIsError(){
        val response = getCourseListResponse(JSON_COURSE_LIST_ERROR)
        exception.expect(ServiceException::class.java)
        exception.expectMessage("Ud. no se encuentra matriculado en el presente ciclo.")

        response.transform()
    }

    private fun getCourseResponse(jsonString: String) : CourseResponse {
        val type = object : TypeToken<CourseResponse>() {}.type
        return Gson().fromJson<CourseResponse>(jsonString, type)
    }

    private fun getCourseListResponse(jsonString: String) : CourseListResponse {
        val type = object : TypeToken<CourseListResponse>() {}.type
        return Gson().fromJson<CourseListResponse>(jsonString, type)
    }

}