package `in`.webdevlabs.campuscommerce

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class CardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        val list = ArrayList<Post>();
        prepareList(list);
        val rView = findViewById(R.id.my_recycler_view) as RecyclerView;
        val adapter = CustomAdapter(this, list)
        rView.adapter = adapter;
        rView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
    private fun prepareList(list : ArrayList<Post>){
        list.add(Post("post1"))
        list.add(Post("post2"))
        list.add(Post("post2"))
        list.add(Post("post2"))
        list.add(Post("post2"))
        list.add(Post("post2"))
        list.add(Post("post2"))
    }
}
