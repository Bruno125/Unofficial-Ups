package com.brunoaybar.unofficalupc.data.source.remote.responses;

import com.brunoaybar.unofficalupc.data.models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunoaybar on 16/10/2016.
 */

public class CourseListResponse extends BaseResponse {

    private List<CourseResponse> Cursos;

    public List<Course> transform(){
        if (isError())
            throw new ServiceException(this);
        List<Course> courses = new ArrayList<>();
        if(Cursos!=null){
            for (CourseResponse response : Cursos)
                courses.add(response.transform());
        }
        return courses;
    }

    public List<CourseResponse> getCursos() {
        return Cursos;
    }

    public void setCursos(List<CourseResponse> cursos) {
        Cursos = cursos;
    }
}
