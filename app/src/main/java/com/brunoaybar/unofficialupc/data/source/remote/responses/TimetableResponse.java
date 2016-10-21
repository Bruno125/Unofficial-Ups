package com.brunoaybar.unofficialupc.data.source.remote.responses;

import com.brunoaybar.unofficialupc.data.models.Timetable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by brunoaybar on 13/10/2016.
 */

public class TimetableResponse extends BaseResponse
{
    public Timetable transform(){
        if(isError())
            throw new ServiceException(this);

        Timetable timetable = new Timetable();

        if(HorarioDia!=null){
            for(HorarioDia horarioDia : HorarioDia){
                Timetable.Day day = new Timetable.Day();
                //Parse day date
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    day.setDate(sdf.parse(horarioDia.Fecha));
                } catch (ParseException e) {
                    continue;
                }
                day.setCode(Integer.parseInt(horarioDia.CodDia));

                //Add classes to current day
                List<Timetable.Class> listClasses = new ArrayList<>();
                if(horarioDia.Clases!=null){
                    for(Clases clase : horarioDia.Clases){
                        Timetable.Class newClass = new Timetable.Class();
                        newClass.setCourseName(clase.CursoNombre);
                        newClass.setCourseCode(clase.CodCurso);
                        newClass.setRoom(clase.Salon);
                        newClass.setVenue(clase.Sede);
                        newClass.setSection(clase.Seccion);

                        //Determine class start date, time and duration
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hhmmss",Locale.getDefault());
                            Date startDate = sdf.parse(clase.Fecha + " " + clase.HoraInicio);
                            Date endDate = sdf.parse(clase.Fecha + " " + clase.HoraFin);

                            long secs = (endDate.getTime() - startDate.getTime()) / 1000;
                            int hours = (int) (secs / 3600); // class duration

                            newClass.setDate(startDate);
                            newClass.setDuration(hours);

                        } catch (ParseException e) {
                            continue;
                        }

                        listClasses.add(newClass);
                    }
                }
                day.setClasses(listClasses);

                //Add current day to timetable
                timetable.addDay(day);

            }
        }


        return timetable;
    }

    public List<HorarioDia> HorarioDia;

    class HorarioDia
    {
        public List<Clases> Clases;

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

