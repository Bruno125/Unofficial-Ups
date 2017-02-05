package com.brunoaybar.unofficialupc.data.source.remote.responses;

import com.brunoaybar.unofficialupc.data.models.Course;

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
                courses.add(response.transform(false));
        }
        return courses;
    }
}
