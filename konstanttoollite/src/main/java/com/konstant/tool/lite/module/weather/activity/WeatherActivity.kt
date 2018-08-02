package com.konstant.tool.lite.module.weather.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.module.weather.param.TitleChanged
import com.konstant.tool.lite.module.weather.fragment.WeatherFragment
import com.konstant.tool.lite.module.weather.param.WeatherStateChanged
import com.konstant.tool.lite.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.Subscribe

/**
 * 描述:天气预报主页
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午3:21
 * 备注:
 */


class WeatherActivity : BaseActivity() {

    private val mFragmentList = mutableListOf<BaseFragment>()
    private val mAdapter by lazy { BaseFragmentAdapter(supportFragmentManager,mFragmentList ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        setTitle("天气查询")
        initBaseViews()
    }


    override fun onSaveInstanceState(outState: Bundle?) {}


    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { startActivity(Intent(this, CityManagerActivity::class.java)) }
        layout_viewpager.offscreenPageLimit = 50
        layout_viewpager.adapter = mAdapter
        title_indicator.setViewPager(layout_viewpager)

        readyFragment()
    }


    private fun readyFragment() {

        val weatherCodeList = CountryManager.readLocalCityList()
        if (weatherCodeList.size > 0) {
            title_indicator.visibility = View.VISIBLE
        } else {
            title_indicator.visibility = View.GONE
        }

        mFragmentList.clear()
        mFragmentList.add(WeatherFragment.newInstance(""))
        weatherCodeList.forEach {
            mFragmentList.add(WeatherFragment.newInstance(it.cityCode))
        }
        mAdapter.notifyDataSetChanged()
        Log.i("mFragmentList size", "${mFragmentList.size}")
    }

    @Subscribe
    fun onTitleChanged(msg: TitleChanged) {
        setTitle(msg.title)
    }

    @Subscribe
    fun onStateChanged(msg: WeatherStateChanged){
        if (msg.cityNumChange){
            readyFragment()
        }
        layout_viewpager.currentItem = msg.index
    }

}

