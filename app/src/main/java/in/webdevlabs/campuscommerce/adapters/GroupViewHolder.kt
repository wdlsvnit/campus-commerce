package `in`.webdevlabs.campuscommerce.adapters

import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Group
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

/**
 * Created by yolo on 8/4/18.
 */

class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var text: TextView = itemView.findViewById(R.id.text) as TextView

    fun bindPost(group: Group) {
        this.text.text = group.gid

    }
}