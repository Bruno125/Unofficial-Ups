package com.brunoaybar.unofficalupc.modules.timetable;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.models.Timetable;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficalupc.modules.base.BaseFragment;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimetableFragment extends BaseFragment {

    @Nullable
    private TimetableViewModel mViewModel;
    @Nullable
    private Timetable mTimetable;

    public static TimetableFragment newInstance(){
        return new TimetableFragment();
    }

    public TimetableFragment() {
        super();
        setFragmentTitle(R.string.option_timetable);
    }

    @BindView(R.id.weekDatePicker) WeekDatePicker weekDatePicker;
    @BindView(R.id.dayScheduleView) DayScheduleView dayScheduleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize ViewModel
        mViewModel = new TimetableViewModel(new UpcRepository(new UserPreferencesDataSource(getContext()), UpcServiceDataSource.getInstance()));
        //Inflate view
        View v  = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.bind(this,v);

        weekDatePicker.setListener(this::updateScheduleForDay);

        return v;
    }

    @Override
    protected void bind() {
        super.bind();
        assert mViewModel != null;

        mSubscription.add(mViewModel.getTimetable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setTimetable,this::displayError));
    }

    private void setTimetable(Timetable timetable){
        mTimetable = timetable;
        updateScheduleForDay(weekDatePicker.getSelectedDate());
    }

    private void updateScheduleForDay(Date selectedDate){
        if(mTimetable==null)
            return;
        dayScheduleView.setDay(mTimetable.getDay(selectedDate));
    }

}
