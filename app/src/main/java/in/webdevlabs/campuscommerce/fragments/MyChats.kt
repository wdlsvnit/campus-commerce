package `in`.webdevlabs.campuscommerce.fragments

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.adapters.GroupViewHolder
import `in`.webdevlabs.campuscommerce.model.Group
import `in`.webdevlabs.campuscommerce.utils.FirebaseUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_mychats.*

class MyChats : Fragment() {

    lateinit var uid: String
    lateinit var text: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mychats, container, false)

        if (FirebaseUtil.isUserSignedIn()) {
            uid = FirebaseAuth.getInstance().currentUser?.uid!!
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val mChatReference = database.reference
            val rView = view.findViewById(R.id.my_recycler_view) as RecyclerView
            val recyclerAdapter = object : FirebaseRecyclerAdapter<Group, GroupViewHolder>(
                    Group::class.java, R.layout.item_mychats,
                    GroupViewHolder::class.java, mChatReference.child("groups").orderByChild("ruid").equalTo(uid)) {
                override fun populateViewHolder(viewHolder: GroupViewHolder?, model: Group?, position: Int) {
                    if (model != null) {
                        viewHolder?.bindPost(model)
                    }
                }
            }
            rView.setHasFixedSize(true)
            rView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rView.adapter = recyclerAdapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        invalidate()
    }

    private fun invalidate() {
        if (!FirebaseUtil.isUserSignedIn()) {
            my_recycler_view.visibility = View.GONE
            get_started.visibility = View.VISIBLE
        } else {
            my_recycler_view.visibility = View.VISIBLE
            get_started.visibility = View.GONE
        }
    }
}
