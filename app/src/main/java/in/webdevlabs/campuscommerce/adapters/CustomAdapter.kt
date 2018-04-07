package `in`.webdevlabs.campuscommerce.adapters

/**
 * Created by yolo on 7/4/18.
 */
import `in`.webdevlabs.campuscommerce.R
import `in`.webdevlabs.campuscommerce.model.Post
import android.content.Context
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class CustomAdapter(private val context : Context, private val list : List<Post>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.card_title) as TextView
        }
    }
    override fun onCreateViewHolder(parent : ViewGroup, type : Int) : ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false);
        return ViewHolder(view);
    }
    override fun onBindViewHolder(holder : ViewHolder, position : Int){
        var post : Post = list.get(position)
        holder.titleTextView.text = post.name;
    }
    override fun getItemCount() : Int{
        return list.size;
    }

}