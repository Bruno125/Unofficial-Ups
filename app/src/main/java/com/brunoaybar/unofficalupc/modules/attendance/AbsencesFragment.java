package com.brunoaybar.unofficalupc.modules.attendance;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.models.Absence;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficalupc.modules.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Fragment that shows the attendance record for the user
 */
public class AbsencesFragment extends BaseFragment {

    private AbsencesViewModel mViewModel;

    public static AbsencesFragment newInstance(){
        return new AbsencesFragment();
    }

    public AbsencesFragment() {
        setFragmentTitle(R.string.option_attendance);
    }


    @BindView(R.id.rviAbsences) RecyclerView rviAbsences;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    private AbsencesAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Init view model
        mViewModel = new AbsencesViewModel((new UpcRepository(new UserPreferencesDataSource(getContext()), UpcServiceDataSource.getInstance())));
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_attendance, container, false);
        ButterKnife.bind(this,v);

        rviAbsences.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar.setVisibility(View.VISIBLE);

        if(mAdapter==null){
            mAdapter = new AbsencesAdapter(getContext());
            mSubscription.add(mViewModel.getAttendanceStream()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::addAbsence,this::displayError));
        }else{
            progressBar.setVisibility(View.GONE);
        }

        rviAbsences.setAdapter(mAdapter);

        return v;
    }

    private void addAbsence(Absence absence){
        progressBar.setVisibility(View.GONE);
        mAdapter.addAbsence(absence);
    }

}
