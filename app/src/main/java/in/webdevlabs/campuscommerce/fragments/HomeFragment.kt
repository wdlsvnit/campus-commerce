package `in`.webdevlabs.campuscommerce.fragments

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.activities.NewPostActivity
import `in`.webdevlabs.campuscommerce.adapters.CustomAdapter
import `in`.webdevlabs.campuscommerce.model.Post
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val mPostReference = database.getReference()

        val list = ArrayList<Post>();
        val rView = view.findViewById(R.id.my_recycler_view) as RecyclerView;
        val adapter = CustomAdapter(activity!!.baseContext, list)

        mPostReference.child("posts").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear()
                    for (ds in dataSnapshot.children) {
                        val post = ds.getValue<Post>(Post::class.java)
                        list.add(Post(post!!.pid, post.name, post.price, post.uid, post.time, post.type, post.tags))
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(firebaseError: DatabaseError) {
            }
        })

        rView.adapter = adapter
        rView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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
