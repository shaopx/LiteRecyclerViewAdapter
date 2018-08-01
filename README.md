# LiteRecyclerViewAdapter
一个kotlin语言实现的RecyclerView的数据显示模式.

#### 使用方法
1. 创建Adapter类对象, 并设置给RecyclerView控件
```
        var adapter = Adapter(this)
        recyclerView.adapter = adapter
```

2. 创建显示数据类
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
