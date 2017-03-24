package com.brunoaybar.unofficialupc.modules.reserve

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.modules.base.BaseFragment


class ReserveFragment : BaseFragment(){
    init {
        setFragmentTitle(R.string.title_reserve)
    }

    companion object {
        @JvmStatic fun newInstance(): ReserveFragment { return ReserveFragment(); }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater?.inflate(R.layout.fragment_reserves,container,false)!!
        ButterKnife.bind(this,v)
        return v
    }


    @OnClick(R.id.btnSearch)
    fun openReserveFilters(){
        val i = Intent(activity.applicationContext,ReserveFiltersActivity::class.java)
        startActivity(i)

    }

}