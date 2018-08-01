package com.spx.bean

class HotScreenResult {
    var count: Int = 0
    var start: Int = 0
    var total: Int = 0
    var title: String? = null
    var subjects: MutableList<SubjectsBean>? = null

    class SubjectsBean {

        var rating: RatingBean? = null
        var title: String? = null
        var collect_count: Int = 0
        var images: ImagesBean? = null
        var id: String? = null
        var casts: List<CastsBean>? = null
        var directors: List<CastsBean>? = null
    }
}