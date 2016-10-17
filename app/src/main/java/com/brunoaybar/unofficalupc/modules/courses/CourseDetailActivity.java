package com.brunoaybar.unofficalupc.modules.courses;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.data.models.Assessment;
import com.brunoaybar.unofficalupc.data.models.Course;
import com.brunoaybar.unofficalupc.data.source.UpcRepository;
import com.brunoaybar.unofficalupc.data.source.preferences.UserPreferencesDataSource;
import com.brunoaybar.unofficalupc.data.source.remote.UpcServiceDataSource;
import com.brunoaybar.unofficalupc.modules.base.BaseActivity;
import com.brunoaybar.unofficalupc.modules.classmates.ClassmatesActivity;
import com.github.lzyzsd.circleprogress.DonutProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

public class CourseDetailActivity extends BaseActivity {

    @Nullable
    CoursesViewModel mViewModel;
    Course mCourse;

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

        mViewModel = new CoursesViewModel(new UpcRepository(new UserPreferencesDataSource(this), UpcServiceDataSource.getInstance()));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void bind() {
        super.bind();
        assert mViewModel != null;

        mSubscription.add(mViewModel.getCourseFromBundle(getIntent().getExtras())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::bindCourse,this::displayError));
    }

    private void bindCourse(Course course){
        mCourse= course;
        //Display course information
        setTitle(course.getName());
        tviCourseName.setText(course.getName());
        tviFormula.setText(course.getFormula());
        progressView.setProgress((int)course.getCurrentProgress());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.classmates,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_classmates:
                if(mCourse!=null)
                    ClassmatesActivity.startActivity(this,mCourse.getCode());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayError(Throwable error) {
        super.displayError(error);
        finish();
    }
}
