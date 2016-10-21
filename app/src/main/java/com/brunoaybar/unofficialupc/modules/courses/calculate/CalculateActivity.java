package com.brunoaybar.unofficialupc.modules.courses.calculate;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.data.source.UpcRepository;
import com.brunoaybar.unofficialupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficialupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficialupc.modules.base.BaseActivity;
import com.brunoaybar.unofficialupc.modules.courses.CoursesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;

public class CalculateActivity extends BaseActivity {


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.btnCalculate) Button btnCalculate;
    @BindView(R.id.calculationsView) CalculationsView calculationsView;
    @BindView(R.id.llaContent) LinearLayout llaContent;

    private CalculateViewModel mViewModel;
    private CoursesViewModel mCourseViewModel;
    private Course mCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewModel = new CalculateViewModel(this);
        mCourseViewModel = new CoursesViewModel(new UpcRepository(new UserPreferencesDataSource(this), UpcServiceDataSource.getInstance()));

        btnCalculate.setOnClickListener(v -> mViewModel.requestedCalculate(calculationsView.getItems()));
    }


    @Override
    protected void bind() {
        super.bind();
        assert mViewModel != null;
        assert mCourseViewModel!=null;

        //Clear view
        calculationsView.removeAllViews();
        //Listen for calculation updates
        calculationsView.setListener(mViewModel::updatedCalculation);

        //Get course information, and then use the assessments to populate the calculations view
        mSubscription.add(mCourseViewModel.getCourseFromBundle(getIntent().getExtras())
                .subscribe(course -> {
                    mCourse = course;
                    mViewModel.getCalculationsStream(course)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(calculationsView::addCalculation,this::displayError);
                },this::displayError));

        //Listen for calculation updates notifications
        mSubscription.add(mViewModel.getCalculationUpdateStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(update -> calculationsView.updateItem(update.getCalculation(),update.getPosition()),this::displayError));

        //Listen for calculation results
        mSubscription.add(mViewModel.getCalculationResultsStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayResult));

    }

    private void displayResult(CalculateViewModel.CalculationResult result){
        if(result.didFail()){ //Show message if failed to calculate
            showToast(result.getMessage());
        }else{ //Display result
            new SweetAlertDialog(this, result.isApproved() ? SweetAlertDialog.SUCCESS_TYPE : SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(result.getTitle())
                    .setContentText(result.getMessage())
                    .setConfirmText(getString(R.string.text_accept))
                    .show();

            //Notify event to analytics
            AnalyticsManager.eventCalculate(this,mCourse,result.getGrade());

        }

    }

}
