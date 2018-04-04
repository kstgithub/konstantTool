package com.konstant.toollite.activity

import android.os.Bundle
import android.view.View
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.util.Constant
import com.konstant.toollite.util.FileUtils
import kotlinx.android.synthetic.main.activity_theme.*
import com.konstant.toollite.eventbusparam.ThemeChanged
import org.greenrobot.eventbus.EventBus


class ThemeActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        selector_red.setOnClickListener(this)
        selector_class.setOnClickListener(this)
        selector_blue.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var thme = Constant.THEME_BLUE
        when (v.id) {
            R.id.selector_red -> {
                thme = Constant.THEME_RED
            }
            R.id.selector_class -> {
                thme = Constant.THEME_CLASS
            }
            R.id.selector_blue -> {
                thme = Constant.THEME_BLUE
            }
        }
        saveSelectedTheme(thme)
        EventBus.getDefault().post(ThemeChanged())

    }


    private fun saveSelectedTheme(theme: String) {
        FileUtils.saveDataWithSharedPreference(this, Constant.NAME_SELECTED_THEME, theme)
    }
}