package com.brunoaybar.unofficialupc.data.source.remote.responses

import com.brunoaybar.unofficialupc.data.models.ReserveOption
import java.text.SimpleDateFormat

class ReserveAvailabilityResponse(val TipoRecurso: String,
                                  val FecReserva: String,
                                  val CanHoras: String,
                                  val Recursos: Array<SingleResourceResponse>) : BaseResponse(){

    class SingleResourceResponse(val CodRecurso: Int,
                          val NomRecurso: String,
                          val Local: String,
                          val FecReserva: String,
                          val HoraIni: String,
                          val HoraFin: String){

        //Example: "24032017 0800" = March 24, 2017 @ 08:00am
        private val DATE_FORMAT = "ddMMyyyy hhmm"

        fun transform(duration: Int): ReserveOption{
            val code = CodRecurso
            val name = NomRecurso
            val venue = Local
            val dateString = "${FecReserva} ${HoraIni}"
            val date = SimpleDateFormat("ddMMyyyy hhmm").parse(dateString)
            val option = ReserveOption("$code", name, venue,date,duration)
            return option
        }
    }

    fun transform(): List<ReserveOption>{
        val duration = CanHoras.toInt()
        return Recursos.map { it.transform(duration) }
    }


}