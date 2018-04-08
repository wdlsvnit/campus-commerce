package `in`.webdevlabs.campuscommerce.fragments

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.adapters.PostViewHolder
import `in`.webdevlabs.campuscommerce.adapters.StringViewHolder
import `in`.webdevlabs.campuscommerce.model.Post
import `in`.webdevlabs.campuscommerce.utils.FirebaseUtil
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_subscription.*

class SubscriptionFragment : Fragment() {

    var uid = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subscription, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                showDialog()
            }

        })

        if (FirebaseUtil.isUserSignedIn()) {
            uid = FirebaseAuth.getInstance().currentUser?.uid!!
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val mTagReference = database.reference
            val rView = view.findViewById(R.id.my_recycler_view) as RecyclerView

            val recyclerAdapter = object : com.firebase.ui.database.FirebaseRecyclerAdapter<String, StringViewHolder>(
                    String::class.java, R.layout.item_tag,
                    StringViewHolder::class.java, mTagReference.child("users").child(uid)) {
                override fun populateViewHolder(viewHolder: StringViewHolder?, model: String?, position: Int) {
                    if (model != null) {
                        viewHolder?.bindPost(model)
                        Toast.makeText(context,model,Toast.LENGTH_LONG)
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

    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog, null)
        dialogBuilder.setView(dialogView)

        val editText = dialogView.findViewById<View>(R.id.editTag) as EditText

        dialogBuilder.setTitle("Tag")
        dialogBuilder.setMessage("Enter Tag Below")
        dialogBuilder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, whichButton ->
            FirebaseUtil.addTagToFirebaseDatabase(editText.text.toString(),uid)
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->
        })
        val b = dialogBuilder.create()
        b.show()
    }
}
