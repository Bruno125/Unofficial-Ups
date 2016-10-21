package com.brunoaybar.unofficialupc.modules.classmates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.brunoaybar.unofficialupc.Injection;
import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.data.models.Classmate;
import com.brunoaybar.unofficialupc.modules.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

public class ClassmatesActivity extends BaseActivity {

    private ClassmatesViewModel mViewModel;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.rviClassmates) RecyclerView rviClassmates;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private ClassmatesAdapter mAdapter;
    private String courseCode;
    private static final String PARAM_COURSE_COUSE = "param_course_code";

    public static void startActivity(Activity activity, String courseCode){
        Intent i = new Intent(activity,ClassmatesActivity.class);
        i.putExtra(PARAM_COURSE_COUSE,courseCode);
        activity.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classmates);
        ButterKnife.bind(this);

        courseCode = getIntent().getStringExtra(PARAM_COURSE_COUSE);
        if(TextUtils.isEmpty(courseCode)){
            showToast(R.string.error_getting_classmates);
            finish();
            return;
        }

        mViewModel = new ClassmatesViewModel(Injection.provideUpcRepository(this));

        //Setup toolbar
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set progress
        progressBar.setIndeterminate(true);
    }

    @Override
    protected void bind() {
        super.bind();
        assert mViewModel != null;

        progressBar.setVisibility(View.VISIBLE);
        setupAdapter();
        mSubscription.add(mViewModel.getClassmates(courseCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setClassmates,this::displayError));
    }

    private void setupAdapter(){

        rviClassmates.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ClassmatesAdapter(this);
        rviClassmates.setAdapter(mAdapter);
    }

    private void setClassmates(List<Classmate> classmates){
        progressBar.setVisibility(View.GONE);
        for(Classmate classmate : classmates)
            mAdapter.addClassmate(classmate);
    }


}
