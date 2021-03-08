package com.jetbrains.handson.app.truthoraction.list

import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.app.truthoraction.R
import com.jetbrains.handson.app.truthoraction.edititems.EditItemDialog
import com.jetbrains.handson.app.truthoraction.viewmodels.ItemViewModel

class ViewerList(private val list: ItemViewModel, private val supportFragmentManager: FragmentManager): RecyclerView.Adapter<ViewerList.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_displayed_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list.customItems.value?.get(position)
        holder.itemView.findViewById<ImageButton>(R.id.item_dots_menu).setOnClickListener {
            val popup = PopupMenu(it.context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.edit_delete_list_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.edit_button -> {
                        val dialog = EditItemDialog(position, list)
                        dialog.show(supportFragmentManager, "edit_item")
                        true
                    }
                    R.id.delete_button -> {
                        list.removeItem(position)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun getItemCount(): Int = list.customItems.value?.size?:0
}