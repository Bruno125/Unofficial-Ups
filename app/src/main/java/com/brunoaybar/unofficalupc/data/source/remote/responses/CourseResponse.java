package com.brunoaybar.unofficalupc.data.source.remote.responses;

import com.brunoaybar.unofficalupc.data.models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 13/10/2016.
 */

public class CourseResponse extends BaseResponse
{
    private String CursoNombre;

    private String CodCurso;

    public Course transform(){
        Course course = new Course();
        course.setCode(getCodCurso());
        course.setName(getCursoNombre());
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

}
