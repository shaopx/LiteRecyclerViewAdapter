package com.spx.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView

/**
 * 通用 RecyclerView.Adapter类, 基本结构与普通RecyclerView.Adapter一致.
 * 内部使用一个arrayList列表变量dataItems来存储数据, 提供了addData和replace两个方法来更新数据
 * 在onCreateViewHolder中会根据viewType找到对应的viewHolder类,并创建iewHolder类实例, 如果找不到则返回一个空实现EmptyVH实例
 * 在onBindViewHolder中,交给VH实例自己进行数据绑定操作
 */
class Adapter(val context: Context) : RecyclerView.Adapter<VH<Data>>() {
    private val dataItems = ArrayList<Data>()

    override fun getItemViewType(position: Int): Int {
        return dataItems[position].itemViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<Data> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        VHList[viewType]?.let {
            return it(view) as VH<Data>
        }
        return EmptyVH(view)
    }

    override fun onBindViewHolder(holder: VH<Data>, position: Int) {
        holder.bind(dataItems[position], position)
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }

    fun addData(list: List<Data>) {
        dataItems.addAll(list)
        notifyDataSetChanged()
    }

    fun replace(list: List<Data>) {
        dataItems.run {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
}
