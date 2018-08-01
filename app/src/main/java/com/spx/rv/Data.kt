package com.spx.rv

import android.view.View

interface Data {
    val itemViewType: Int
}
open class CommonData(
        override var itemViewType: Int,
        vHConstructorFuc: (view: View)-> VH<out Data>)
    : Data {

    init {
        VHList.register(itemViewType, vHConstructorFuc)
    }
}