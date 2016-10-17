package com.brunoaybar.unofficalupc.modules.general;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.brunoaybar.unofficalupc.R;
import com.brunoaybar.unofficalupc.modules.attendance.AbsencesFragment;
import com.brunoaybar.unofficalupc.modules.base.BaseFragment;
import com.brunoaybar.unofficalupc.modules.courses.CoursesFragment;
import com.brunoaybar.unofficalupc.modules.timetable.TimetableFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private HashMap<String,BaseFragment> mFragments;

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupDrawer();
        setupFragments();

    }

    public void setupDrawer(){
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setupFragments(){
        mFragments = new HashMap<>();
        setFragment(TimetableFragment.newInstance());
        navigationView.setCheckedItem(R.id.nav_timetable);
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
        drawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timetable) {
            // Handle the camera action
            setFragment(TimetableFragment.newInstance());
        } else if (id == R.id.nav_courses) {
            setFragment(CoursesFragment.newInstance());
        } else if (id == R.id.nav_attendance) {
            setFragment(AbsencesFragment.newInstance());
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
