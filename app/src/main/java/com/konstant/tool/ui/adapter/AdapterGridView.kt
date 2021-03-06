package com.konstant.tool.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.konstant.tool.R

/**
 * 描述:城市管理页面的适配器
 * 创建人:菜籽
 * 创建时间:2018/1/3 下午7:07
 * 备注:
 */

class AdapterGridView(val context: Context, val list: List<String>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = mInflater.inflate(R.layout.item_gridview_city_manager, parent, false)
        val textView = view.findViewById(R.id.tv_item_city) as TextView
        textView.text = list[position]
        return view
    }
}