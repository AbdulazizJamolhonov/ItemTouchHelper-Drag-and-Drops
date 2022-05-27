package developer.abdulaziz.homework14_1.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import developer.abdulaziz.homework14_1.Cache.MyShared
import developer.abdulaziz.homework14_1.Models.User
import developer.abdulaziz.homework14_1.R
import kotlinx.android.synthetic.main.item_rv.view.*
import java.util.*

class UserAdapter(
    var context: Context,
    var list: ArrayList<User>
) :
    RecyclerView.Adapter<UserAdapter.Vh>(),
    developer.abdulaziz.homework14_1.Utils.ItemTouchHelperAdapter {
    inner class Vh(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(user: User, position: Int) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.rv_anim)
            itemView.startAnimation(animation)
            itemView.tv_item_artist.text = user.artist
            itemView.tv_item_music.text = user.music
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

//    interface RvClick {
//        fun onClick(user: User, position: Int)
//    }


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition > toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition until toPosition + 1) {
                Collections.swap(list, i, i)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int, context: Context) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel: ${list[position].music}")
//        MyShared.list = list
        context.startActivity(intent)
        notifyItemChanged(position)
    }
}