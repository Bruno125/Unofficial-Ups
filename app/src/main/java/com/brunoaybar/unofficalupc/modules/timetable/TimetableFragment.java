package com.brunoaybar.unofficalupc.modules.timetable;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.modules.general.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends BaseFragment {

    public static TimetableFragment newInstance(){
        return new TimetableFragment();
    }

    public TimetableFragment() {
        setFragmentTitle(R.string.option_timetable);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_timetable, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    @Override
    public void onPause() {
        unbind();
        super.onPause();
    }

    private void bind() {

    }

    private void unbind() {
    }

}
