package com.brunoaybar.unofficalupc.data.source.remote.responses;

import com.brunoaybar.unofficalupc.data.models.Timetable;

import java.util.List;

/**
 * Created by brunoaybar on 13/10/2016.
 */

public class TimetableResponse extends BaseResponse
{
    public Timetable transform(){
        if(isError())
            throw new ServiceException(this);
        else
            return new Timetable();
    }

    public List<HorarioDia> HorarioDia;

    class HorarioDia
    {
        public List<Clases> Clases;

        public String CodAlumno;

        public String Fecha;

        public String CodDia;
    }

    class Clases
    {
        public String Seccion;

        public String Sede;

        public String Fecha;

        public String CursoNombre;

        public String Salon;

        public String HoraInicio;

        public String HoraFin;

        public String CodCurso;
    }
}

