package com.brunoaybar.unofficialupc.data.source.remote.responses

import com.brunoaybar.unofficialupc.data.models.ReserveOption
import java.text.SimpleDateFormat

class ReserveAvailabilityResponse(val TipoRecurso: String?,
                                  val FecReserva: String?,
                                  val CanHoras: String?,
                                  val Recursos: Array<SingleResourceResponse>?) : BaseResponse(){

    class SingleResourceResponse(val CodRecurso: Int,
                          val NomRecurso: String,
                          val Local: String,
                          val FecReserva: String,
                          val HoraIni: String,
                          val HoraFin: String){

        //Example: "24032017 0800" = March 24, 2017 @ 08:00am
        private val DATE_FORMAT = "ddMMyyyy hhmm"

        fun transform(type: String, duration: Int): ReserveOption{
            val code = CodRecurso
            val name = NomRecurso
            val venue = Local
            val dateString = "${FecReserva} ${HoraIni}"
            val date = SimpleDateFormat("ddMMyyyy hhmm").parse(dateString)
            val option = ReserveOption("$code", type, name, venue,date,duration)
            return option
        }
    }

    fun transform(): List<ReserveOption>{
        if(isError){
            throw ServiceException(this)
        }

        val duration = CanHoras?.toInt() ?: 0
        val type = TipoRecurso ?: "None"
        return Recursos?.map { it.transform(type,duration) } ?: arrayListOf()
    }


}