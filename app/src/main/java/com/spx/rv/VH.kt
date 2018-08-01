package com.spx.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 自定义的RecyclerView.ViewHolder子类.主要是为了增加bind(data: Data, position: Int)方法
 */
abstract class VH<T : Data>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T, position: Int)
}

/**
 * ViewHolder的注册表, 建立itemViewType与ViewHolder的对应关系
 * 存放在map中, 效率凑合
 */
object VHList {
    private var vhs = mutableMapOf<Int, (view: View) -> VH<out Data>>()

    fun register(viewType: Int, vHConstructorFuc: (view: View) -> VH<out Data>) {
        vhs[viewType] = vHConstructorFuc
    }

    operator fun get(viewType: Int): ((view: View) -> VH<out Data>)? = vhs[viewType]
}

/**
 * 这是一个空ViewHolder实现, 只会抛出异常
 */
class EmptyVH(itemView: View) : VH<Data>(itemView) {
    override fun bind(data: Data, position: Int) {
        TODO("not implemented")
    }
}