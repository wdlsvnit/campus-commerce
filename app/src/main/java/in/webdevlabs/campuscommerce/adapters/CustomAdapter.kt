package `in`.webdevlabs.campuscommerce.adapters

/**
 * Created by yolo on 7/4/18.
 */
import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Post
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class CustomAdapter(val list: ArrayList<Post>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name) as TextView
        var price: TextView = itemView.findViewById(R.id.price) as TextView
        var type: TextView = itemView.findViewById(R.id.type) as TextView
        var chat: ImageView = itemView.findViewById(R.id.chat) as ImageView
        var time: TextView = itemView.findViewById(R.id.time) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post: Post = list[position]

        holder.name.text = post.name
        holder.price.text = post.price.toString()
        holder.type.text = post.type.toString()

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        post.time?.run {
            holder.time.text = DateUtils.getRelativeTimeSpanString(sdf.parse(post.time).time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}