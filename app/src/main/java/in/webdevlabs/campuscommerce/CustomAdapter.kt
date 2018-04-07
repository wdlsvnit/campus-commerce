package `in`.webdevlabs.campuscommerce

/**
 * Created by yolo on 7/4/18.
 */
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.card_layout.*



class CustomAdapter(private val context : Context, private val list : List<Post>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.card_title) as TextView
        }
    }
    override fun onCreateViewHolder(parent : ViewGroup, type : Int) : CustomAdapter.ViewHolder{
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false);
        return ViewHolder(view);
    }
    override fun onBindViewHolder(holder : CustomAdapter.ViewHolder, position : Int){
        var post : Post = list.get(position)
        holder.titleTextView.text = post.name;
    }
    override fun getItemCount() : Int{
        return list.size;
    }

}