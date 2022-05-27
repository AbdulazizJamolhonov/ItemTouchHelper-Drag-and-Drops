package developer.abdulaziz.homework14_1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import developer.abdulaziz.homework14_1.Adapters.UserAdapter
import developer.abdulaziz.homework14_1.Cache.MyShared
import developer.abdulaziz.homework14_1.Models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.alert_dialog.view.*

@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<User>
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Contact"

        MyShared.init(this)
        list = MyShared.list

        userAdapter = UserAdapter(this@MainActivity, list)

        add_user.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
            val itemDialog =
                layoutInflater.inflate(R.layout.alert_dialog, null, false)
            alertDialog.setView(itemDialog)
            alertDialog.show()
            itemDialog.btn_save.setOnClickListener {
                val artist = itemDialog.edt_artist.text.toString().trim()
                val music = itemDialog.edt_music.text.toString().trim()
                if (artist != "" && music != "") {
                    list.add(User(artist, music))
                    MyShared.list = list
                    userAdapter.notifyItemChanged(list.lastIndex)
                    alertDialog.cancel()
                } else {
                    Toast.makeText(this, "Ma'lumot yetarli emas !", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val itemTouchHelper = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                userAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                MyShared.list = list
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userAdapter.onItemDismiss(viewHolder.adapterPosition, this@MainActivity)
//                userAdapter.notifyDataSetChanged()
//                MyShared.list = list
            }
        }

        val itemTouch = ItemTouchHelper(itemTouchHelper)
        itemTouch.attachToRecyclerView(rv)

        rv.adapter = userAdapter
    }
}
