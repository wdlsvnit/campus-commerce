package `in`.webdevlabs.campuscommerce.fragments

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.utils.FirebaseUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_subscription.*

class SubscriptionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subscription, container, false)

        if (FirebaseUtil.isUserSignedIn()) {
            /*val uid = FirebaseAuth.getInstance().currentUser?.uid!!
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
            rView.adapter = recyclerAdapter*/
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
