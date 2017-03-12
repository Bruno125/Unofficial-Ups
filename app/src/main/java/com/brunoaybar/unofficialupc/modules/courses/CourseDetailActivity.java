package com.brunoaybar.unofficialupc.modules.courses;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.Assessment;
import com.brunoaybar.unofficialupc.data.models.Course;
import com.brunoaybar.unofficialupc.modules.base.BaseActivity;
import com.brunoaybar.unofficialupc.modules.classmates.ClassmatesActivity;
import com.brunoaybar.unofficialupc.modules.courses.calculate.CalculateActivity;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class CourseDetailActivity extends BaseActivity {

    @Nullable
    CoursesViewModel mViewModel;
    Course mCourse;

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tviCourseName) TextView tviCourseName;
    @BindView(R.id.tviFormula) TextView tviFormula;
    @BindView(R.id.progressView) DonutProgress progressView;

    @BindView(R.id.footerView) AssessmentRowView footerView;
    @BindView(R.id.llaAssessments) LinearLayout llaAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        ButterKnife.bind(this);

        mViewModel = new CoursesViewModel();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("");
        mSubscription.add(mViewModel.getCourseFromBundle(getIntent().getExtras())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindCourse,this::displayError));
    }


    @Override
    protected void bind() {
        super.bind();
        assert mViewModel != null;

        mSubscription.add(mViewModel.getCourseDetails()
                .subscribe(this::openCalculate, e->{}));

    }

    private void bindCourse(Course course){
        progressBar.setVisibility(View.GONE);
        mCourse= course;
        //Display course information
        setTitle(course.getName());
        tviCourseName.setText(course.getName());
        tviFormula.setText(course.getFormula());
        animateToProgress((int)course.getCurrentProgress());

        //Display assessments
        llaAssessments.removeAllViews();
        for(Assessment assessment : course.getAssesments()){
            AssessmentRowView view = new AssessmentRowView(getApplicationContext(),assessment);
            llaAssessments.addView(view);
        }

        //Display current progress
        Assessment currentGrade = new Assessment();
        currentGrade.setName(getString(R.string.text_final_assessment));
        currentGrade.setWeight(course.getCurrentProgress());
        currentGrade.setGrade(String.valueOf(course.getCurrentGrade()));
        footerView.setAssessment(this,currentGrade);
    }

    private void animateToProgress(int progress){
        int currentProgress = progressView.getProgress();
        int var = currentProgress - progress;
        boolean isIncreasing = currentProgress < progress;
        List<Integer> states = new ArrayList<>();
        while(currentProgress!=progress){
            currentProgress+= isIncreasing ? 1 : -1;
            states.add(currentProgress);
        }

        Observable<Long> mTimerObservable = Observable.interval(20,TimeUnit.MILLISECONDS);
        Observable<Integer> mProgressObservable = Observable.just(states).flatMapIterable(i -> i);

        Observable.zip(mTimerObservable, mProgressObservable,(t,p) -> p)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p ->{
                    progressView.setProgress(p);
                },e ->{
                    Log.e("ERROR",e.toString());
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_classmates:
                if(mCourse!=null)
                    ClassmatesActivity.startActivity(this,mCourse.getCode());
                return true;
            case R.id.action_calculate:
                if(mCourse!=null && mViewModel!=null) {
                    mViewModel.courseAvailable(mCourse);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openCalculate(Bundle bundle){
        Intent i = new Intent(CourseDetailActivity.this, CalculateActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void displayError(Throwable error) {
        super.displayError(error);
        finish();
    }
}
