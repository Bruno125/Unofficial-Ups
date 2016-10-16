package com.brunoaybar.unofficalupc.modules.courses;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public static CoursesFragment newInstance(){
        return new CoursesFragment();
    }

    public CoursesFragment() {
        setFragmentTitle(R.string.option_courses);
    }


    @BindView(R.id.tviCourse) TextView tviCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Init view model
        mViewModel = new CoursesViewModel(new UpcRepository(new UserPreferencesDataSource(getContext()), UpcServiceDataSource.getInstance()));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    protected void bind() {
        super.bind();
        mSubscription.add(mViewModel.getCoursesStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addCourse,this::displayError));
    }

    private void addCourse(Course course){
        String current = tviCourse.getText().toString();
        current = current.concat(course.getName() + "\n");
        tviCourse.setText(current);
    }

}
