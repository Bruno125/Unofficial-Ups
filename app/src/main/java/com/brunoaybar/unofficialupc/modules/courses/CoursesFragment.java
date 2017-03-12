package com.brunoaybar.unofficialupc.modules.courses;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.modules.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Fragment that displays the courses in which the user is currently enrolled
 */
public class CoursesFragment extends BaseFragment {


    private CoursesViewModel mViewModel;
    private CoursesAdapter mAdapter;


    public static CoursesFragment newInstance(){
        return new CoursesFragment();
    }

    public CoursesFragment() {
        setFragmentTitle(R.string.option_courses);
    }

    @BindView(R.id.rviCourses) RecyclerView rviCourses;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    private List<Course> mCourses;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Init view model
        mViewModel = new CoursesViewModel();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        ButterKnife.bind(this,view);

        //Setup views
        progressBar.setIndeterminate(true);
        setRecyclerView();

        //Start listening for courses
        if(mCourses == null) {
            progressBar.setVisibility(View.VISIBLE);
            mSubscription.add(mViewModel.getCoursesStream()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::addCourses, this::displayError));
        }else{
            addCourses(mCourses);
        }

        return view;
    }

    private void setRecyclerView(){
        rviCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CoursesAdapter(getContext(),mViewModel::courseAvailable);
        rviCourses.setAdapter(mAdapter);
    }

    @Override
    protected void bind() {
        super.bind();

        mSubscription.add(mViewModel.getCourseDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showCourseDetail,this::displayError));
    }

    private void addCourses(List<Course> courses){
        mCourses = courses;
        progressBar.setVisibility(View.GONE);
        for(Course course : courses)
            mAdapter.addCourse(course);
    }

    private void showCourseDetail(Bundle courseBundle){
        Intent i = new Intent(getActivity(),CourseDetailActivity.class);
        i.putExtras(courseBundle);
        startActivity(i);
    }

}
