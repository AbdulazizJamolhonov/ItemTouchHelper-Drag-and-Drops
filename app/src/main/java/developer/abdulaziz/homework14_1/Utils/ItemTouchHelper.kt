package developer.abdulaziz.homework14_1.Utils

import android.content.Context

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int, context: Context)
}