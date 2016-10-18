package com.brunoaybar.unofficalupc.modules.general;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.modules.attendance.AbsencesFragment;
import com.brunoaybar.unofficalupc.modules.base.BaseFragment;
import com.brunoaybar.unofficalupc.modules.courses.CoursesFragment;
import com.brunoaybar.unofficalupc.modules.timetable.TimetableFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private HashMap<String,BaseFragment> mFragments;


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

    }

    private void setupFragments(){
        mFragments = new HashMap<>();
        mSelectedTab = R.id.nav_timetable;
    }

    public void setFragment(BaseFragment fragment){
        String title = fragment.getTitle(this);
        if(mFragments.containsKey(title))
            fragment = mFragments.get(title);
        else
            mFragments.put(title,fragment);
        // Insert the fragment by replacing any existing fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.flContent, fragment).commit();
        //Set toolbar title and close drawer
        setTitle(title);
    }

    private @IdRes int mSelectedTab;
    private void handleTabSelection(int tabId){
        mSelectedTab = tabId;
        if (tabId == R.id.nav_timetable) {
            setFragment(TimetableFragment.newInstance());
        } else if (tabId == R.id.nav_courses) {
            setFragment(CoursesFragment.newInstance());
        } else if (tabId == R.id.nav_attendance) {
            setFragment(AbsencesFragment.newInstance());
        }

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
                .setPositiveButton(R.string.text_yes, (dialog, which) -> logout())
                .setNegativeButton(R.string.text_no, null)
                .create().show();
    }

    private void logout(){
    }

}
