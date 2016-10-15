package com.brunoaybar.unofficalupc.modules.courses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.modules.general.BaseFragment;

/**
 * Fragment that displays the courses in which the user is currently enrolled
 */
public class CoursesFragment extends BaseFragment {


    public static CoursesFragment newInstance(){
        return new CoursesFragment();
    }

    public CoursesFragment() {
        setFragmentTitle(R.string.option_courses);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

}
