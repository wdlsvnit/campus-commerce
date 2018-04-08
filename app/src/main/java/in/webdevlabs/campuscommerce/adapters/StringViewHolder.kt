package `in`.webdevlabs.campuscommerce.adapters

import `in`.webdevlabs.campuscommerce.App
import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.activities.ChatActivity
import `in`.webdevlabs.campuscommerce.model.Group
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

/**
 * Created by yolo on 8/4/18.
 */
class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var text: TextView = itemView.findViewById(R.id.tags) as TextView

    fun bindPost(tag: String) {
        text.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                text.text = tag
            }

        })
    }
}