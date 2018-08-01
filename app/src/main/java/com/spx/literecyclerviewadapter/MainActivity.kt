package com.spx.literecyclerviewadapter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        simple_list.setOnClickListener {
            val intent = Intent(this@MainActivity, SimpleListActivity::class.java)
            startActivity(intent)
        }

        complicated_list.setOnClickListener {
            val intent = Intent(this@MainActivity, NotSimpleListActivity::class.java)
            startActivity(intent)
        }

    }
}
