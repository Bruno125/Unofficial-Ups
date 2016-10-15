package com.brunoaybar.unofficalupc.modules.attendance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.modules.general.BaseFragment;

/**
 * Fragment that shows the attendance record for the user
 */
public class AttendanceFragment extends BaseFragment {

    public static AttendanceFragment newInstance(){
        return new AttendanceFragment();
    }

    public AttendanceFragment() {
        setFragmentTitle(R.string.option_attendance);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

}
