package `in`.webdevlabs.campuscommerce.adapters

/**
 * Created by yolo on 7/4/18.
 */
import `in`.webdevlabs.campuscommerce.App
import `in`.webdevlabs.campuscommerce.activities.ChatActivity
import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Post
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.flexbox.FlexboxLayout
import fisk.chipcloud.ChipCloud
import java.text.SimpleDateFormat
import java.util.*


class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView = itemView.findViewById(R.id.name) as TextView
    var price: TextView = itemView.findViewById(R.id.price) as TextView
    var flexbox: FlexboxLayout = itemView.findViewById(R.id.flexBox) as FlexboxLayout
    var type: TextView = itemView.findViewById(R.id.type) as TextView
    var time: TextView = itemView.findViewById(R.id.time) as TextView
    val chipCloud = ChipCloud(itemView.context, flexbox)

    fun bindPost(post: Post) {
        this.name.text = post.name
        this.price.text = post.price.toString()

        Toast.makeText(itemView.context, post.toString(), Toast.LENGTH_SHORT).show()

        post.tags?.let {
            this.chipCloud.addChips(it)
        }

        this.type.text = post.type.toString()

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        post.time?.run {
            time.text = DateUtils.getRelativeTimeSpanString(sdf.parse(post.time).time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
        }
        chat.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent(App.applicationContext(), ChatActivity::class.java)
                intent.putExtra("uid",post.uid)
                intent.putExtra("pid",post.pid)
                App.applicationContext().startActivity(intent)
            }

        })
    }
}