package `in`.webdevlabs.campuscommerce.fragments

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.activities.NewPostActivity
import `in`.webdevlabs.campuscommerce.adapters.PostViewHolder
import `in`.webdevlabs.campuscommerce.model.Post
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val mPostReference = database.reference

        val rView = view.findViewById(R.id.my_recycler_view) as RecyclerView;

        val recyclerAdapter = object : FirebaseRecyclerAdapter<Post, PostViewHolder>(
                Post::class.java, R.layout.item_post,
                PostViewHolder::class.java, mPostReference.child("posts")) {

            override fun populateViewHolder(viewHolder: PostViewHolder?, post: Post?, position: Int) {
                if (post != null) {
                    viewHolder?.bindPost(post)
                }
            }
        }

        rView.setHasFixedSize(true)
        rView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rView.adapter = recyclerAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab.setOnClickListener {
            val intent = Intent(activity, NewPostActivity::class.java)
            startActivity(intent)
        }
    }
}
