package com.brunoaybar.unofficialupc.modules.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.brunoaybar.unofficialupc.Injection;
import com.brunoaybar.unofficialupc.R;
import com.brunoaybar.unofficialupc.analytics.AnalyticsManager;
import com.brunoaybar.unofficialupc.analytics.AppRemoteConfig;
import com.brunoaybar.unofficialupc.modules.attendance.AbsencesFragment;
import com.brunoaybar.unofficialupc.modules.auth.LoginActivity;
import com.brunoaybar.unofficialupc.modules.base.BaseActivity;
import com.brunoaybar.unofficialupc.modules.base.BaseFragment;
import com.brunoaybar.unofficialupc.modules.courses.CoursesFragment;
import com.brunoaybar.unofficialupc.modules.timetable.TimetableFragment;
import com.roughike.bottombar.BottomBar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class MainActivity extends BaseActivity {

    private HashMap<String,BaseFragment> mFragments;
    private MainViewModel mViewModel;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bottomBar) BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupFragments();
        bottomBar.setOnTabSelectListener(this::handleTabSelection);
        mViewModel = new MainViewModel(Injection.provideUpcRepository(this));

        AppRemoteConfig.getInstance().update(this);
    }

    private void setupFragments(){
        mFragments = new HashMap<>();
    }

    public void setFragment(BaseFragment fragment){
        String title = fragment.getTitle(this);
        if(mFragments.containsKey(title))
            fragment = mFragments.get(title);
        else
            mFragments.put(title,fragment);
        // Insert the fragment by replacing any existing fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.flContent, fragment).commit();
        //Set toolbar title and close drawer
        setTitle(title);
    }

    private void handleTabSelection(int tabId){
        if (tabId == R.id.nav_timetable) {
            setFragment(TimetableFragment.newInstance());
        } else if (tabId == R.id.nav_courses) {
            setFragment(CoursesFragment.newInstance());
        } else if (tabId == R.id.nav_attendance) {
            setFragment(AbsencesFragment.newInstance());
        }

    }

    @Override
    protected void bind() {
        super.bind();
        mSubscription.add(mViewModel.getLogoutStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(didLogout ->{
                    if(didLogout) {
                        //Notify event to analytics
                        AnalyticsManager.eventLogout(this);
                        //Redirect to home screen
                        openHome();
                    }else
                        showToast(R.string.error_logout);
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_logout:
                displayLogout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayLogout(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.logout_dialog_title))
                .setMessage(getString(R.string.logout_dialog_message))
                .setPositiveButton(R.string.text_yes, (dialog, which) -> mViewModel.performLogout())
                .setNegativeButton(R.string.text_no, null)
                .create().show();
    }

    private void openHome(){
        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

}
