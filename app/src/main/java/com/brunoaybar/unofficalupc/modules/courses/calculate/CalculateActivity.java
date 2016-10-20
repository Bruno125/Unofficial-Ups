package com.brunoaybar.unofficalupc.modules.courses.calculate;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficalupc.modules.base.BaseActivity;
import com.brunoaybar.unofficalupc.modules.courses.CoursesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;

public class CalculateActivity extends BaseActivity {


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.btnCalculate) Button btnCalculate;

    private CalculateViewModel mViewModel;
    private CoursesViewModel mCourseViewModel;

    private CalculationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel = new CalculateViewModel(this);
        mCourseViewModel = new CoursesViewModel(new UpcRepository(new UserPreferencesDataSource(this), UpcServiceDataSource.getInstance()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnCalculate.setOnClickListener(v -> mViewModel.requestedCalculate(mAdapter.getItems()));
    }


    @Override
    protected void bind() {
        super.bind();
        assert mViewModel != null;
        assert mCourseViewModel!=null;

        setAdapter();
        mSubscription.add(mCourseViewModel.getCourseFromBundle(getIntent().getExtras())
                .subscribe(course -> {
                    mViewModel.getCalculationsStream(course)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(mAdapter::addCalculation,this::displayError);
                },this::displayError));

        mSubscription.add(mViewModel.getCalculationUpdateStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(update -> mAdapter.updateItem(update.getCalculation(),update.getPosition()),this::displayError));

        mSubscription.add(mViewModel.getCalculationResultsStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayResult,this::displayError));

    }

    private void setAdapter(){
        mAdapter = new CalculationAdapter(this);
        mAdapter.setListener(mViewModel::updatedCalculation);
        recyclerView.setAdapter(mAdapter);
    }

    private void displayResult(CalculateViewModel.CalculationResult result){
        new SweetAlertDialog(this, result.isApproved() ? SweetAlertDialog.SUCCESS_TYPE : SweetAlertDialog.WARNING_TYPE)
                .setTitleText(result.getTitle())
                .setContentText(result.getMessage())
                .setConfirmText(getString(R.string.text_accept))
                .show();
    }

}
