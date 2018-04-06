package com.konstant.toollite.fragment

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.konstant.toollite.R
import com.konstant.toollite.adapter.AdapterWeatherDaily
import com.konstant.toollite.adapter.AdapterWeatherHourly
import com.konstant.toollite.base.BaseFragment
import com.konstant.toollite.eventbusparam.TitleChanged
import com.konstant.toollite.server.Service
import com.konstant.toollite.server.response.LocationCIDrResponse
import com.konstant.toollite.server.response.Weather360Response
import com.konstant.toollite.util.KeyConstant
import com.konstant.toollite.util.UrlConstant
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.layout_weather_15_daily.*
import kotlinx.android.synthetic.main.layout_weather_24_hour.*
import kotlinx.android.synthetic.main.layout_weather_current.*
import org.greenrobot.eventbus.EventBus

/**
 * 描述:天气展示页
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午3:22
 * 备注:
 */

class WeatherFragment() : BaseFragment() {

    private lateinit var mLocationClient: AMapLocationClient
    private var mDirectCode: String = ""

    private val mListHour = ArrayList<Weather360Response.HourlyForecastBean>()
    private val mAdapterHour by lazy { AdapterWeatherHourly(mActivity, mListHour) }

    private val mListDaily = ArrayList<Weather360Response.WeatherBean>()
    private val mAdapterDay by lazy { AdapterWeatherDaily(mActivity, mListDaily) }

    private var mCurrentCity = "加载中"

    private val mRequestCode = 11

    companion object {
        private val PARAM = "param"
        fun newInstance(direct: String): Fragment {
            val fragment = WeatherFragment()
            val bundle = Bundle()
            bundle.putString(PARAM, direct)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDirectCode = arguments.getString(PARAM)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_weather, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_weather_hour.layoutManager = LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false)
        recycler_weather_hour.adapter = mAdapterHour

        recycler_weather_day.isNestedScrollingEnabled = false
        recycler_weather_day.layoutManager = LinearLayoutManager(mActivity)
        recycler_weather_day.adapter = mAdapterDay

        refresh_layout.setHeaderView(BezierLayout(mActivity))
        refresh_layout.setEnableLoadmore(false)
        refresh_layout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                if (mDirectCode.isNullOrEmpty()) {
                    mLocationClient.startLocation()
                } else {
                    requestData(mDirectCode)
                }
            }
        })

        if (mDirectCode.isNullOrEmpty()) {
            initLocationClient()
            requestPermission()
        } else {
            refresh_layout.startRefresh()
        }
    }

    private fun requestPermission() {
        AndPermission.with(mActivity)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION)
                .onGranted { mLocationClient.startLocation() }
                .onDenied { Toast.makeText(mActivity, "您拒绝了定位权限", Toast.LENGTH_SHORT).show() }
                .start()
    }


    // 初始化获取当前位置的相关控件
    private fun initLocationClient() {
        mLocationClient = AMapLocationClient(mActivity)
        val option = AMapLocationClientOption()
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationClient.setLocationOption(option)

        mLocationClient.setLocationListener {
            if (it.errorCode != AMapLocation.LOCATION_SUCCESS) {
                Toast.makeText(mActivity, "定位失败，请稍后重试", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("当前位置", "${it.longitude},${it.latitude}")
                val param = "${it.district},${it.city}"
                        .replace("省", "")
                        .replace("市", "")
                        .replace("区", "")
                changeCid(param)
            }
        }
    }

    // 转为城市CID
    private fun changeCid(string: String) {
        Service.locationToCID(UrlConstant.WEATHER_HEFENG_URL, string, KeyConstant.WEATHER_KEY) { state, data ->
            stopRefreshAnim()
            if (!state) return@locationToCID
            val response = JSON.parseObject(data.replace("$", ""), LocationCIDrResponse::class.java)
            mDirectCode = response.heWeather6[0].basic.cid.replace("CN", "")
            Log.d("地区编号", mDirectCode)
            refresh_layout?.startRefresh()

        }
    }

    // 向服务器请求数据
    private fun requestData(location: String) {
        Service.queryWeather(location) { state, data ->
            stopRefreshAnim()
            if (mActivity.isDestroyed or !state) return@queryWeather
            setData(data)
        }
    }

    // 设置数据
    private fun setData(data: String) {
        val result = JSON.parseObject(data, Weather360Response::class.java)
        val realtime = result.realtime
        val hourly_forecast = result.hourly_forecast
        val weatherList = result.weather
        mListHour.addAll(hourly_forecast)
        mListDaily.addAll(weatherList)

        mActivity.runOnUiThread {

            // 头部的信息
            tv_weather_direct.text = realtime.wind.direct
            tv_weather_power.text = realtime.wind.power

            tv_weather_describe.text = "天气：${realtime.weather.info}"
            tv_temperature.text = realtime.weather.temperature

            tv_weather_update_time.text = "更新时间：${realtime.time}"

            // 逐小时预报
            mAdapterHour.notifyDataSetChanged()

            // 逐天预报
            mAdapterDay.notifyDataSetChanged()

            // 更换父标题
            mCurrentCity = "${result.area[0][0]}${result.area[2][0]}"
            if (isFragmentResume()) {
                setActivityTitle(mCurrentCity)
            }

        }
    }

    // 设置标题
    private fun setActivityTitle(title: String) {
        EventBus.getDefault().post(TitleChanged(title))
    }

    // 停止刷新
    private fun stopRefreshAnim() {
        mActivity.runOnUiThread { refresh_layout?.finishRefreshing() }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            setActivityTitle(mCurrentCity)
        }
    }

}