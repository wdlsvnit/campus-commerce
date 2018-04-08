package `in`.webdevlabs.campuscommerce.adapters

import `in`.webdevlabs.campuscommerce.App
import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.activities.ChatActivity
import `in`.webdevlabs.campuscommerce.model.Group
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by yolo on 8/4/18.
 */

class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var text: TextView = itemView.findViewById(R.id.text) as TextView

    fun bindPost(group: Group) {
        this.text.text = group.sname
        text.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(App.applicationContext(),ChatActivity::class.java)

                intent.putExtra("uid",group.ruid)
                intent.putExtra("pid",group.pid)
                App.applicationContext().startActivity(intent)

            }

        })
    }
}