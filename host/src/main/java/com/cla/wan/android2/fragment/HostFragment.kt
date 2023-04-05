package com.cla.wan.android2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cla.wan.android2.R
import com.cla.wan.common.ui.titleBar.ClaTitleBar
import com.cla.wan.common.ui.titleBar.initTitleBar
import com.cla.wan.common.utils.findView

class HostFragment : Fragment() {

    private val titleBar by findView<ClaTitleBar>(R.id.titleBar)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleBar(titleBar) {
            centerTextString = "这是测试"
        }
    }

}