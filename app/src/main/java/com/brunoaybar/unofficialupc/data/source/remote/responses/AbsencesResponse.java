package com.brunoaybar.unofficialupc.data.source.remote.responses;

import com.brunoaybar.unofficialupc.data.models.Absence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class AbsencesResponse extends BaseResponse{

    private List<Inasistencia> Inasistencias;

    public List<Absence> transform(){
        if(isError())
            throw new ServiceException(this);

        List<Absence> absences = new ArrayList<>();
        if(Inasistencias != null)
            for (Inasistencia inasistencia : Inasistencias) {
                Absence absence = new Absence();
                absence.setCode(inasistencia.getCodInasistencia());
                absence.setCourseCode(inasistencia.getCodCurso());
                absence.setCourseName(inasistencia.getCursoNombre());
                absence.setMaximum(Integer.parseInt(inasistencia.getMaximo()));
                absence.setTotal(Integer.parseInt(inasistencia.getTotal()));
                absences.add(absence);
            }
        return absences;
    }

    public List<Inasistencia> getInasistencias() {
        return Inasistencias;
    }

    public void setInasistencias(List<Inasistencia> inasistencias) {
        Inasistencias = inasistencias;
    }

    public class Inasistencia
    {
        private String Maximo;

        private String CodAlumno;

        private String CodInasistencia;

        private String CursoNombre;

        private String Total;

        private String CodCurso;

        public String getMaximo ()
        {
            return Maximo;
        }

        public void setMaximo (String Maximo)
        {
            this.Maximo = Maximo;
        }

        public String getCodAlumno ()
        {
            return CodAlumno;
        }

        public void setCodAlumno (String CodAlumno)
        {
            this.CodAlumno = CodAlumno;
        }

        public String getCodInasistencia ()
        {
            return CodInasistencia;
        }

        public void setCodInasistencia (String CodInasistencia)
        {
            this.CodInasistencia = CodInasistencia;
        }

        public String getCursoNombre ()
        {
            return CursoNombre;
        }

        public void setCursoNombre (String CursoNombre)
        {
            this.CursoNombre = CursoNombre;
        }

        public String getTotal ()
        {
            return Total;
        }

        public void setTotal (String Total)
        {
            this.Total = Total;
        }

        public String getCodCurso ()
        {
            return CodCurso;
        }

        public void setCodCurso (String CodCurso)
        {
            this.CodCurso = CodCurso;
        }

    }



}
