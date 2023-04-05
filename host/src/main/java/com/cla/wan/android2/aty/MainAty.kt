package com.cla.wan.android2.aty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.cla.wan.android2.R
import com.cla.wan.common.utils.findView

class MainAty : AppCompatActivity() {

    private val navFragmentHost by findView<FragmentContainerView>(R.id.navFragmentHost)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aty_main)
    }
}


