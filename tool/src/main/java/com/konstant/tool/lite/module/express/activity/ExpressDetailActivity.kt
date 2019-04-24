package com.konstant.tool.lite.module.express.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.express.SelectorDialog
import com.konstant.tool.lite.module.express.adapter.AdapterExpressDetail
import com.konstant.tool.lite.module.express.data.ExpressManager
import com.konstant.tool.lite.module.express.param.ExpressChanged
import com.konstant.tool.lite.module.express.server.ExpressResponse
import com.konstant.tool.lite.network.NetService
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_express_detail.*
import kotlinx.android.synthetic.main.layout_dialog_input.view.*
import kotlinx.android.synthetic.main.pop_express.view.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.EventBus
import java.util.*

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

        ExpressManager.addExpress(mOrderNo, mCompanyId, mRemark, "暂无信息")
        sendExpressChanged()

        listview_detail.adapter = mAdapter

        btn_retry.setOnClickListener { queryExpress() }

        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { onMorePressed() }
    }

    // 开始查询物流信息
    private fun queryExpress() {
        onLoading()
        NetService.expressQuery(mCompanyId,mOrderNo){
            if (it.status != "200") {
                onError()
                return@expressQuery
            }

            onSuccess()
            updateData(it)
        }
    }

    // 更新界面
    private fun updateUI() {

        tv_state_express.text = mState

        tv_remark.text = mRemark

        var com = ""
        ids.forEachIndexed { index, _ ->
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

            ExpressManager.updateExpress(mOrderNo, mCompanyId, mRemark, mState)
            sendExpressChanged()
        }
    }

    // 正在加载中
    private fun onLoading() {
        runOnUiThread {
            layout_loading_express.visibility = View.VISIBLE
            layout_erroe.visibility = View.GONE
            listview_detail.visibility = View.GONE
        }
    }

    // 加载失败
    private fun onError() {
        runOnUiThread {
            layout_loading_express.visibility = View.GONE
            layout_erroe.visibility = View.VISIBLE
            listview_detail.visibility = View.GONE
        }
    }

    // 加载成功
    private fun onSuccess() {
        runOnUiThread {
            layout_loading_express.visibility = View.GONE
            layout_erroe.visibility = View.GONE
            listview_detail.visibility = View.VISIBLE
        }
    }

    // 右上角的更多按钮按下后
    private fun onMorePressed() {
        with(LayoutInflater.from(this).inflate(R.layout.pop_express, null)){
            tv_change_order.setOnClickListener { changeOrderNo() }
            tv_change_company.setOnClickListener { changeCompany() }
            tv_change_remark.setOnClickListener { changeRemark() }
            tv_delete.setOnClickListener { deleteOrder() }
            mPop = PopupWindow(this, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
            mPop.showAsDropDown(title_bar)
        }
    }

    // 修改物流单号
    private fun changeOrderNo() {
        mPop.dismiss()
        val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
        view.edit_input.setText(mOrderNo)
        KonstantDialog(this)
                .setMessage("输入运单号")
                .addView(view)
                .setPositiveListener {
                    if (TextUtils.isEmpty(view.edit_input.text)) {
                        showToast("记得输入运单号哦")
                        return@setPositiveListener
                    }
                    it.dismiss()
                    ExpressManager.deleteExpress(orderNo = mOrderNo)
                    mOrderNo = view.edit_input.text.toString()
                    updateUI()
                    ExpressManager.addExpress(mOrderNo, mCompanyId, mRemark, mState)
                    sendExpressChanged()
                    queryExpress()
                }
                .createDialog()
    }

    // 修改物流公司
    private fun changeCompany() {
        mPop.dismiss()
        SelectorDialog(this)
                .setOnItemClickListener { id, _ ->
                    mCompanyId = id
                    updateUI()
                    queryExpress()
                    ExpressManager.updateExpress(mOrderNo, mCompanyId, null, null)
                    sendExpressChanged()
                }
                .createDialog()
    }

    // 修改备注
    private fun changeRemark() {
        mPop.dismiss()
        val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
        val edit = view.edit_input
        edit.setText(mRemark)
        KonstantDialog(this)
                .setMessage("输入备注")
                .addView(view)
                .setPositiveListener {
                    it.dismiss()
                    if (TextUtils.isEmpty(edit.text)) {
                        showToast("记得输入备注哦")
                        return@setPositiveListener
                    }
                    mRemark = edit.text.toString()
                    updateUI()
                    ExpressManager.updateExpress(mOrderNo, null, mRemark, null)
                    sendExpressChanged()
                }
                .createDialog()
    }

    // 删除运单
    private fun deleteOrder() {
        mPop.dismiss()
        KonstantDialog(this)
                .setMessage("确定要删除此运单号？")
                .setPositiveListener {
                    it.dismiss()
                    ExpressManager.deleteExpress(orderNo = mOrderNo)
                    sendExpressChanged()
                    this.finish()
                }
                .createDialog()
    }

    // 发送物流信息变化的广播
    private fun sendExpressChanged() {
        EventBus.getDefault().post(ExpressChanged())
    }

}
