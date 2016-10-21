package com.brunoaybar.unofficialupc.data.source.remote.responses;

import com.brunoaybar.unofficialupc.data.models.Classmate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 17/10/2016.
 */

public class ClassmatesResponse
{
    private String curso;

    private Alumnos[] alumnos;

    private String MsgError;

    private String cursoId;

    private String seccion;

    private String CodError;

    public List<Classmate> transform(){
        List<Classmate> result = new ArrayList<>();
        if(alumnos!=null){
            for(Alumnos alumno : alumnos)
                result.add(alumno.transform());
        }

        return result;
    }


    public String getCurso ()
    {
        return curso;
    }

    public void setCurso (String curso)
    {
        this.curso = curso;
    }

    public Alumnos[] getAlumnos ()
    {
        return alumnos;
    }

    public void setAlumnos (Alumnos[] alumnos)
    {
        this.alumnos = alumnos;
    }

    public String getMsgError ()
    {
        return MsgError;
    }

    public void setMsgError (String MsgError)
    {
        this.MsgError = MsgError;
    }

    public String getCursoId ()
    {
        return cursoId;
    }

    public void setCursoId (String cursoId)
    {
        this.cursoId = cursoId;
    }

    public String getSeccion ()
    {
        return seccion;
    }

    public void setSeccion (String seccion)
    {
        this.seccion = seccion;
    }

    public String getCodError ()
    {
        return CodError;
    }

    public void setCodError (String CodError)
    {
        this.CodError = CodError;
    }

    class Alumnos
    {
        private String codigo;

        private String carrera_actual;

        private String url_foto;

        private String nombre_completo;

        public Classmate transform(){
            Classmate classmate = new Classmate();
            classmate.setCode(getCodigo());
            classmate.setName(getNombre_completo());
            classmate.setCareer(getCarrera_actual());
            classmate.setPhoto(getUrl_foto());
            return classmate;
        }


        public String getCodigo ()
        {
            return codigo;
        }

        public void setCodigo (String codigo)
        {
            this.codigo = codigo;
        }

        public String getCarrera_actual ()
        {
            return carrera_actual;
        }

        public void setCarrera_actual (String carrera_actual)
        {
            this.carrera_actual = carrera_actual;
        }

        public String getUrl_foto ()
        {
            return url_foto;
        }

        public void setUrl_foto (String url_foto)
        {
            this.url_foto = url_foto;
        }

        public String getNombre_completo ()
        {
            return nombre_completo;
        }

        public void setNombre_completo (String nombre_completo)
        {
            this.nombre_completo = nombre_completo;
        }
    }

}