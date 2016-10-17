package com.brunoaybar.unofficalupc.modules.courses;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficalupc.modules.base.BaseFragment;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Init view model
        mViewModel = new CoursesViewModel(new UpcRepository(new UserPreferencesDataSource(getContext()), UpcServiceDataSource.getInstance()));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        ButterKnife.bind(this,view);

        //Set recyclerView
        setRecyclerView();

        progressBar.setIndeterminate(true);

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

        progressBar.setVisibility(View.VISIBLE);
        setRecyclerView();

        mSubscription.add(mViewModel.getCoursesStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addCourse,this::displayError));


        mSubscription.add(mViewModel.getCourseDetails()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showCourseDetail,this::displayError));
    }

    private void addCourse(Course course){
        progressBar.setVisibility(View.GONE);
        mAdapter.addCourse(course);
    }

    private void showCourseDetail(Bundle courseBundle){
        Intent i = new Intent(getActivity(),CourseDetailActivity.class);
        i.putExtras(courseBundle);
        startActivity(i);
    }

}
