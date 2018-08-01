package com.spx.net

import com.spx.bean.HotScreenResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

const val BASE_URL:String = "https://api.douban.com/"
const val API_KEY:String = "0b2bdeda43b5688921839c8ecb20399b"

interface DouBanApi {

    /**
     * 获取正在上映的数据
     */
    @GET("v2/movie/in_theaters")
    fun getHotScreenList(@QueryMap par: Map<String, String>): Observable<HotScreenResult>
}