package `in`.webdevlabs.campuscommerce.fragments

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.adapters.CustomAdapter
import `in`.webdevlabs.campuscommerce.model.Post
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val list = ArrayList<Post>();
        prepareList(list);
        val rView = view.findViewById(R.id.my_recycler_view) as RecyclerView;
        val adapter = CustomAdapter(activity!!.baseContext, list)
        rView.adapter = adapter;
        rView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        return view
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
