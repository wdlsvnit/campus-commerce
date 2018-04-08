package `in`.webdevlabs.campuscommerce.adapters

/**
 * Created by yolo on 7/4/18.
 */
import `in`.webdevlabs.campuscommerce.App
import `in`.webdevlabs.campuscommerce.ChatActivity
import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Chat
import `in`.webdevlabs.campuscommerce.model.Post
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var time: TextView = itemView.findViewById(R.id.time_chat) as TextView
    var msg: TextView = itemView.findViewById(R.id.msg) as TextView

    fun bindPost(chat: Chat) {
        this.msg.text=chat.msg

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        chat.time?.run {
            time.text = DateUtils.getRelativeTimeSpanString(sdf.parse(chat.time).time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
        }
    }
}