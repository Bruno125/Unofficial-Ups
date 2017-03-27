package com.brunoaybar.unofficialupc.modules.reserve.filters

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.modules.base.BaseActivity

/**
 * Created by fanlat on 23/03/17.
 */


class ReserveFiltersActivity : BaseActivity(){

    @BindView(R.id.contentFrame) lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_filters)
        ButterKnife.bind(this)

        setupFragment()
    }

    fun setupFragment(){
        supportFragmentManager.beginTransaction().add(R.id.contentFrame, ReserveFiltersFragment.newInstance()).commit()
    }

}