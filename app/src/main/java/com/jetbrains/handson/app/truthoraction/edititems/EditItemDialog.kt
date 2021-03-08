package com.jetbrains.handson.app.truthoraction.edititems

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.jetbrains.handson.app.truthoraction.R
import com.jetbrains.handson.app.truthoraction.viewmodels.ItemViewModel

open class EditItemDialog(private val index: Int, private val items: ItemViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            val dialogWindow = inflater.inflate(R.layout.edit_item_dialog, null)

            // Fill in input field with item's text to be edited
            dialogWindow.findViewById<EditText>(R.id.edit_item_field).setText(items.customItems.value?.get(index))

            builder.setView(dialogWindow)
                .setTitle("Редактировать")
                .setPositiveButton("Принять") { _, _ ->
                    val input = dialogWindow.findViewById<EditText>(R.id.edit_item_field)
                    if (input.text.toString().isNotEmpty()) {
                        items.addItem(input.text.toString(), index)
                    }
                }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}