package com.spx.literecyclerviewadapter

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spx.bean.HotScreenResult
import com.spx.net.API_KEY
import com.spx.net.ApiService
import com.spx.rv.Adapter
import com.spx.rv.CommonData
import com.spx.rv.Data
import com.spx.rv.VH
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_simple_list.*

class NotSimpleListActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_list)

        setupRecyclerView(recyclerview)

        loadOnlineData()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = Adapter(this)
        recyclerView.adapter = adapter
    }

    /**
     * 判断当前网络是否可用
     */
    fun hasNet(): Boolean {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable
    }

    private fun loadOnlineData() {

        if (!hasNet()) {
            Toast.makeText(this, "请先联网", LENGTH_LONG).show()
            return
        }

        val parm = mapOf("apikey" to API_KEY,
                "city" to "北京",
                "start" to "0",
                "count" to "30")
        ApiService.get()!!
                .getHotScreenList(parm)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError = {
                            Log.e("NotSimpleListActivity", "error", it)
                        },
                        onNext = {
                            onResult(it!!)
                        }
                )
    }

    private fun onResult(resut: HotScreenResult) {
        var data = resut.subjects?.map {
            HotScreenData(it, R.layout.douban_hotscreen_item_layout, ::HotScreenDataViewHolder)
        }
        adapter.addData(data!!)
    }
}

/**
 * define the wrapper data
 */
class HotScreenData(var backdata: HotScreenResult.SubjectsBean,
                    layoutId: Int,
                    funct: (view: View) -> VH<out Data>)
    : CommonData(layoutId, funct)

class HotScreenDataViewHolder : VH<HotScreenData> {
    var title: TextView? = null
    var dy: TextView? = null
    var actor: TextView? = null
    var watchNum: TextView? = null
    var rating: RatingBar? = null
    var image: ImageView? = null

    constructor(itemView: View) : super(itemView) {
        itemView.apply {
            title = findViewById(R.id.item_hot_screen_title)
            image = findViewById(R.id.item_hot_screen_image)
            rating = findViewById(R.id.item_hot_screen_rating)
            dy = findViewById(R.id.item_hot_screen_dy)
            actor = findViewById(R.id.item_hot_screen_actor)
            watchNum = findViewById(R.id.item_hot_screen_watch_num)
        }
    }

    override fun bind(data: HotScreenData, position: Int) {
        title?.text = data.backdata.title
        Glide.with(itemView.context).load(data.backdata.images?.small).into(image!!)
        rating?.rating = (data.backdata.rating?.average!! / 2).toFloat()
        dy?.text = "导演：" + data.backdata.directors!![0].name
        watchNum?.text = "${data.backdata.collect_count} 人看过"
        actor?.text = "主演：" + data.backdata.casts!!.map { it.name }
    }
}