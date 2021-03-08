package com.jetbrains.handson.app.truthoraction.additems

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.jetbrains.handson.app.truthoraction.viewmodels.ItemViewModel
import com.jetbrains.handson.app.truthoraction.R

open class AddItemDialog(private val title: String, private val items: ItemViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            val dialogWindow = inflater.inflate(R.layout.add_item_dialog, null)
            builder.setView(dialogWindow)
                .setTitle("Добавить $title")
                .setPositiveButton("Добавить") { _, _ ->
                    val input = dialogWindow.findViewById<EditText>(R.id.item_input)
                    if (input.text.toString().isNotEmpty()) {
                        items.addItem(input.text.toString())
                    }
                }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}