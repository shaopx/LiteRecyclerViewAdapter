# LiteRecyclerViewAdapter
一个kotlin语言实现的RecyclerView的数据显示模式.

#### 使用方法
1. 创建Adapter类对象, 并设置给RecyclerView控件
```
        var adapter = Adapter(this)
        recyclerView.adapter = adapter
```

2. 创建显示数据类

假设这个例子中我们只需要在recyclerview的每个条目中显示一个字符串. 
而且我们已经有了一个字符串列表.作为要显示的数据源.
```
/**
 * define the wrapper data
 */
class StringData(var str: String,
                 layoutId: Int,
                 funct: (view: View) -> VH<out Data>)
    : CommonData(layoutId, funct)

/**
 * define the view holder for StringData above
 */
class StringDataViewHolder(itemView: View) : VH<StringData>(itemView) {
    override fun bind(data: StringData, position: Int) {
        var simple_tv: TextView =itemView?.findViewById(R.id.simple_tv)
        simple_tv.text = data.str
    }
}
```
StringData类对象就是在adapter中使用的数据集类
StringDataViewHolder类就是对应的ViewHolder
这两个自定义类可以放到一个单独的kt文件中, 或者放到recyclerview控件使用的代码附近. 

StringData有三个参数, 分别是源数据实例, UI布局文件, 对应的view holder的构造方法(这里就是StringDataViewHolder的构造方法)


2. 创建数据集合, 并设置给adapter
```
        adapter.addData(data.map {
            StringData(it, R.layout.simple_list_item_layout, ::StringDataViewHolder)
        })
```

#### 示例
-------------------------------------
再看一个稍微复杂点的例子:
```
class NotSimpleListActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_list)

        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = Adapter(this)
        recyclerview.adapter = adapter

        loadOnlineData()
    }

    private fun loadOnlineData() {
        ApiService.get()!!
                .getHotScreenList(mapOf("apikey" to API_KEY,  "count" to "30"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onError ={ Log.e("NotSimpleListActivity", "error", it) },
                        onNext = {
                            adapter.addData(it!!.subjects?.map {
                                HotScreenData(it, R.layout.douban_hotscreen_item_layout, ::HotScreenDataViewHolder)
                            }!!)
                        }
                )
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
```
如上这些代码就能完整显示下面这个页面:
![豆瓣电影网络接口数据](http://...)

与前面介绍的不同的, 只是定义了新的数据类HotScreenData  和对应的 HotScreenDataViewHolder
而显示相关的所有逻辑都集中在HotScreenDataViewHolder中, 这与android原始的使用方法是一致的. 
而因为数据类和viewholder类集成在一起, 目的是高聚合.方便检查代码.
而在使用中又避免了每个页面创建一个自定义RecyclerView.Adapter, 减少了代码, 聚焦业务.

