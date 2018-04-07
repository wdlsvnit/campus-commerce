package `in`.webdevlabs.campuscommerce.adapters

/**
 * Created by yolo on 7/4/18.
 */
import `in`.webdevlabs.campuscommerce.ChatActivity
import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Post
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class CustomAdapter(private val context : Context, private val list : List<Post>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var price: TextView
        var type: TextView
        var chat: ImageView
        var time: TextView

        init {
            name = itemView.findViewById(R.id.name) as TextView
            price = itemView.findViewById(R.id.price) as TextView
            type = itemView.findViewById(R.id.type) as TextView
            time = itemView.findViewById(R.id.time) as TextView
            chat = itemView.findViewById(R.id.chat) as ImageView

        }
    }
    override fun onCreateViewHolder(parent : ViewGroup, type : Int) : ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false);
        return ViewHolder(view);
    }
    override fun onBindViewHolder(holder : ViewHolder, position : Int){
        var post : Post = list.get(position)
        holder.name.text = post.name

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        holder.time.text = DateUtils.getRelativeTimeSpanString(sdf.parse(post.time).time,System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS)
        holder.price.text = post.price.toString()
        holder.type.text = post.type.toString()
        holder.chat.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                context.startActivity(Intent(context,ChatActivity::class.java))
            }

        })
    }
    override fun getItemCount() : Int{
        return list.size;
    }

}