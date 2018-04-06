package com.konstant.toollite.activity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.alibaba.fastjson.JSON
import com.konstant.toollite.R
import com.konstant.toollite.adapter.AdapterExpressDetail
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.data.ExpressManager
import com.konstant.toollite.server.Service
import com.konstant.toollite.server.response.ExpressResponse
import com.konstant.toollite.view.KonstantConfirmtDialog
import com.konstant.toollite.view.KonstantArrayAdapter
import com.konstant.toollite.view.KonstantInputDialog
import com.konstant.toollite.view.KonstantViewDialog
import kotlinx.android.synthetic.main.activity_express_detail.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 描述:物流详情页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class ExpressDetailActivity : BaseActivity() {

    var mState = ""

    var mCompanyId = ""
    var mOrderNo = ""
    var mRemark = ""

    val coms by lazy { resources.getStringArray(R.array.express_company) }
    val ids by lazy { resources.getStringArray(R.array.express_company_id) }

    lateinit var mPop: PopupWindow

    val mDatas = ArrayList<ExpressResponse.DataBean>()
    val mAdapter by lazy { AdapterExpressDetail(this, mDatas) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express_detail)

        setTitle("物流详情")

        initBaseViews()

        queryExpress()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        mCompanyId = intent.getStringExtra("mCompanyId")
        mOrderNo = intent.getStringExtra("mOrderNo")
        mRemark = intent.getStringExtra("mRemark") ?: "保密物件"

        updateUI()

        ExpressManager.addExpress(this, mOrderNo, mCompanyId, mRemark, "暂无信息")

        listview_detail.adapter = mAdapter

        btn_retry.setOnClickListener { queryExpress() }

        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { onMorePressed() }
    }

    // 开始查询物流信息
    private fun queryExpress() {
        onLoading()
        Service.expressQuery(mCompanyId, mOrderNo) { state, data ->
            if (!state) {
                onError()
                return@expressQuery
            }
            val response = JSON.parseObject(data, ExpressResponse::class.java)
            Log.d(this.localClassName,data)
            if (response.status != "200") {
                onError()
                return@expressQuery
            }

            onSuccess()
            updateData(response)

        }
    }

    // 更新界面
    private fun updateUI() {

        tv_state.text = mState

        tv_remark.text = mRemark


        var com = ""
        ids.forEachIndexed { index, s ->
            if (mCompanyId == ids[index]) {
                com = coms[index]
            }
        }
        tv_num.text = "$com:$mOrderNo"
    }

    // 刷新数据
    private fun updateData(response: ExpressResponse) {
        runOnUiThread {
            mDatas.addAll(response.data)
            mAdapter.notifyDataSetChanged()

            var com = ""
            val comName = resources.getStringArray(R.array.express_company)
            val comID = resources.getStringArray(R.array.express_company_id)

            comID.forEachIndexed { index, s ->
                if (s == response.com) {
                    com = comName[index]
                }
            }

            when (response.state) {
                0 -> mState = "在途中"
                1 -> mState = "已揽件"
                2 -> mState = "疑难件"
                3 -> mState = "已签收"
                4 -> mState = "已退签"
                5 -> mState = "派件中"
                6 -> mState = "退回中"
            }

            updateUI()

            updateLocalData(mOrderNo, mCompanyId, mRemark, mState)
        }
    }

    // 更新本地缓存数据
    private fun updateLocalData(orderNo: String, companyId: String, remark: String, state: String) {
        Log.d(this.localClassName,"状态："+state)
        ExpressManager.updateExpress(this, orderNo, companyId, remark, state)
    }

    // 正在加载中
    private fun onLoading() {
        runOnUiThread {
            layout_loading.visibility = View.VISIBLE
            layout_erroe.visibility = View.GONE
            listview_detail.visibility = View.GONE
        }

    }

    // 加载失败
    private fun onError() {
        runOnUiThread {
            layout_loading.visibility = View.GONE
            layout_erroe.visibility = View.VISIBLE
            listview_detail.visibility = View.GONE
        }

    }

    // 加载成功
    private fun onSuccess() {
        runOnUiThread {
            layout_loading.visibility = View.GONE
            layout_erroe.visibility = View.GONE
            listview_detail.visibility = View.VISIBLE
        }
    }

    // 右上角的更多按钮按下后
    private fun onMorePressed() {
        val view = LayoutInflater.from(this).inflate(R.layout.pop_express, null)
        view.findViewById<TextView>(R.id.tv_change_order).setOnClickListener { changeOrderNo() }
        view.findViewById<TextView>(R.id.tv_change_company).setOnClickListener { changeCompany() }
        view.findViewById<TextView>(R.id.tv_change_remark).setOnClickListener { changeRemark() }
        view.findViewById<TextView>(R.id.tv_delete).setOnClickListener { deleteOrder() }

        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        mPop.showAsDropDown(title_bar)

    }

    // 修改物流单号
    private fun changeOrderNo() {
        mPop.dismiss()
        KonstantInputDialog(this)
                .setMessage("输入运单号")
                .setPositiveListener {
                    ExpressManager.deleteExpress(this, mOrderNo)
                    mOrderNo = it
                    updateUI()
                    ExpressManager.addExpress(this, mOrderNo, mCompanyId, mRemark, mState)
                    queryExpress()
                }
                .show()
    }

    // 修改物流公司
    private fun changeCompany() {
        mPop.dismiss()
        val view = LayoutInflater.from(this).inflate(R.layout.layout_spinner, null)

        val spinner = view.findViewById<Spinner>(R.id.spinner_company)
        spinner.adapter = KonstantArrayAdapter(this, R.layout.item_spinner_bg, coms)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mCompanyId = ids[position]
            }
        }

        KonstantViewDialog(this)
                .setMessage("选择物流公司：")
                .addView(view)
                .setPositiveListener {
                    it.dismiss()
                    updateUI()
                    queryExpress()
                    ExpressManager.updateExpress(this, mOrderNo, mCompanyId, null, null)
                }
                .show()
    }

    // 修改备注
    private fun changeRemark() {
        mPop.dismiss()
        KonstantInputDialog(this)
                .setMessage("输入备注")
                .setPositiveListener {
                    mRemark = it
                    updateUI()
                    ExpressManager.updateExpress(this, mOrderNo, null, mRemark, null)
                }
                .show()
    }

    // 删除运单
    private fun deleteOrder() {
        mPop.dismiss()
        KonstantConfirmtDialog(this)
                .setMessage("确定要删除此运单号？")
                .setPositiveListener {
                    it.dismiss()
                    ExpressManager.deleteExpress(this, mOrderNo)
                    this.finish()
                }
                .setNegativeListener {  }
                .show()
    }

}