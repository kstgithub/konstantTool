package com.konstant.tool.lite.module.wxfake

import android.content.Context
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.util.FileUtil

class AdapterFake(val list: List<Conversion>) : BaseRecyclerAdapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        context = viewGroup.context
        return when (viewType) {
            0 -> {
                AdverseHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_conversion_adverse, viewGroup, false))
            }
            else -> {
                MineHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_conversion_mine, viewGroup, false))
            }
        }
    }

    override fun getItemCount()= list.size

    override fun getItemViewType(position: Int) = list[position].type

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val conversion = list[position]
        with(holder.itemView){
            val bitmap = FileUtil.getBitmap(context, conversion.fileName)
            with(RoundedBitmapDrawableFactory.create(resources, bitmap)) {
                paint.isAntiAlias = true
                cornerRadius = 50F
                findViewById<ImageView>(R.id.img_header).setImageDrawable(this)
            }
            findViewById<TextView>(R.id.tv_msg).text = conversion.msg
        }
    }

    class AdverseHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    class MineHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)
}