package com.brunoaybar.unofficialupc.modules.reserve

import android.os.Bundle
import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.modules.base.BaseActivity

class ReserveOptionsActivity : BaseActivity(){

    companion object {
        @JvmStatic val dataParam = "param_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_options)

        val data = intent.extras.get(dataParam)
        if (data != null) {
            print("Data: $data")
        }

    }

}