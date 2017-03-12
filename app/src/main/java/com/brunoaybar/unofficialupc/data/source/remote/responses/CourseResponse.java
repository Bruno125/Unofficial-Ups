package com.brunoaybar.unofficialupc.data.source.remote.responses;

import android.text.TextUtils;

import com.brunoaybar.unofficialupc.data.models.Assessment;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 13/10/2016.
 */

public class CourseResponse extends BaseResponse
{
    private String CursoNombre;

    private String CodCurso;

    private String Formula;

    private String PorcentajeAvance;

    private String NotaFinal;

    private List<Nota> Notas;

    public Course transform(){
        return transform(true);
    }

    public Course transform(boolean withDetails){
        Course course = new Course();
        course.setCode(getCodCurso());
        course.setName(getCursoNombre());
        course.setFormula(getFormula());

        if(!withDetails){
            course.setValid(true);
            return course;
        }

        try{
            course.setCurrentGrade(Double.parseDouble(getNotaFinal()));
        }catch (Exception e){ //If grade is empty or not double mark as error
            course.setValid(false);
            return course;
        }


        if(Utils.isEmpty(getPorcentajeAvance()))
            course.setCurrentProgress(0);
        else
            course.setCurrentProgress(Double.parseDouble(getPorcentajeAvance().replace("%","")));

        List<Assessment> assessments = new ArrayList<>();
        if(Notas!=null){
            for(Nota nota : Notas)
                assessments.add(nota.transform());
        }
        course.setAssesments(assessments);

        return course;
    }

    public String getCursoNombre ()
    {
        return CursoNombre;
    }

    public void setCursoNombre (String CursoNombre)
    {
        this.CursoNombre = CursoNombre;
    }

    public String getCodCurso ()
    {
        return CodCurso;
    }

    public void setCodCurso (String CodCurso)
    {
        this.CodCurso = CodCurso;
    }

    public String getFormula() {
        return Formula;
    }

    public void setFormula(String formula) {
        Formula = formula;
    }

    public String getPorcentajeAvance() {
        return PorcentajeAvance;
    }

    public void setPorcentajeAvance(String porcentajeAvance) {
        PorcentajeAvance = porcentajeAvance;
    }

    public String getNotaFinal() {
        return NotaFinal;
    }

    public void setNotaFinal(String notaFinal) {
        NotaFinal = notaFinal;
    }

    class Nota{
        private String CodNota;
        private String NombreEvaluacion;
        private String NombreCorto;
        private int NroEvaluacion;
        private String Peso;
        private String Valor;

        public Assessment transform(){
            Assessment assessment = new Assessment();
            assessment.setCode(getCodNota());
            assessment.setGrade(getValor());
            assessment.setIndex(String.valueOf(NroEvaluacion));
            assessment.setName(getNombreEvaluacion());
            assessment.setNickname(getNombreCorto());
            assessment.setWeight(Double.parseDouble(getPeso().replace("%","")));
            return assessment;
        }

        public String getNombreEvaluacion() {
            return NombreEvaluacion;
        }

        public void setNombreEvaluacion(String nombreEvaluacion) {
            NombreEvaluacion = nombreEvaluacion;
        }

        public String getNombreCorto() {
            return NombreCorto;
        }

        public void setNombreCorto(String nombreCorto) {
            NombreCorto = nombreCorto;
        }

        public int getNroEvaluacion() {
            return NroEvaluacion;
        }

        public void setNroEvaluacion(int nroEvaluacion) {
            NroEvaluacion = nroEvaluacion;
        }

        public String getPeso() {
            return Peso;
        }

        public void setPeso(String peso) {
            Peso = peso;
        }

        public String getValor() {
            return Valor;
        }

        public void setValor(String valor) {
            Valor = valor;
        }

        public String getCodNota() {
            return CodNota;
        }

        public void setCodNota(String codNota) {
            CodNota = codNota;
        }
    }

}
