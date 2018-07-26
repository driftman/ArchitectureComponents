package com.driftman.fuckingandroid.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by abk on 26/07/2018.
 */
abstract class BaseActivity: AppCompatActivity(), BaseContract.IBaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}