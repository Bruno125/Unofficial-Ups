package com.brunoaybar.unofficalupc.data.source.remote.responses;

import com.brunoaybar.unofficalupc.data.models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 13/10/2016.
 */

public class CoursesResponse extends BaseResponse
{

    private List<Curso> Cursos;

    public  List<Course> transform(){
        if(isError())
            throw new ServiceException(this);

        if(getCursos() == null)
            setCursos(new ArrayList<>());

        List<Course> result = new ArrayList<>();
        for (Curso curso : getCursos()) {
            Course course = new Course();
            course.setCode(curso.getCodCurso());
            course.setName(curso.getCursoNombre());
            result.add(course);
        }
        return result;
    }


    public List<Curso> getCursos ()
    {
        return Cursos;
    }

    public void setCursos (List<Curso> Cursos)
    {
        this.Cursos = Cursos;
    }

    class Curso
    {

        private String CursoNombre;

        private String CodCurso;


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
    }

}
