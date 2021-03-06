package com.konstant.tool.ui.activity.toolactivity.im.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.R

/**
 * 描述:发现页面
 * 创建人:菜籽
 * 创建时间:2018/1/10 下午4:52
 * 备注:
 */

class FinderFragment: androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance(): androidx.fragment.app.Fragment {
            return FinderFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_conversion_finder,container,false)
    }

}