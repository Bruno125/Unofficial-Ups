package com.brunoaybar.unofficialupc.modules.reserve.options

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.modules.base.BaseActivity

class ReserveOptionsActivity : BaseActivity(){

    companion object {
        @JvmStatic val dataParam = "param_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_options)
        setupFragment()
    }

    fun getOptionsData(): String{
        return intent.extras.getString(dataParam)
    }

    fun setupFragment(){
        val bundle = Bundle()
        bundle.putString(ReserveOptionsFragment.dataParam,getOptionsData())

        val fragment = ReserveOptionsFragment.newInstance()
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,fragment)
                .commit()
    }

}