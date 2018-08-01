package com.spx.literecyclerviewadapter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spx.rv.Adapter
import com.spx.rv.CommonData
import com.spx.rv.Data
import com.spx.rv.VH
import kotlinx.android.synthetic.main.activity_simple_list.*

class SimpleListActivity : AppCompatActivity() {
    var data = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_list)


        // prepare data....
        for (i in 1..100) {
            data.add("this is a string , number: $i")
        }

        setupRecyclerView(recyclerview)

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var adapter = Adapter(this)
        recyclerView.adapter = adapter

        // add data to the adapter
        adapter.addData(data.map {
            StringData(it, R.layout.simple_list_item_layout, ::StringDataViewHolder)
        })
    }
}

/**
 * define the wrapper data
 */
class StringData(var str: String,
                 layoutId: Int,
                 funct: (view: View) -> VH<out Data>) : CommonData(layoutId, funct) {

}

/**
 * define the view holder for StringData above
 */
class StringDataViewHolder : VH<StringData> {
    var simple_tv: TextView

    constructor(itemView: View) : super(itemView) {
        simple_tv = itemView.findViewById(R.id.simple_tv)
    }

    override fun bind(data: StringData, position: Int) {
        simple_tv.text = data.str
    }
}