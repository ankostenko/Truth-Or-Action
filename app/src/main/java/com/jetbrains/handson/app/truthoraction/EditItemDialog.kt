package com.jetbrains.handson.app.truthoraction

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

open class EditItemDialog<SharedViewModel: ItemViewModel>(private val layoutId: Int, private val index: Int, private val editFieldId: Int): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val itemsViewModel: SharedViewModel by activityViewModels()

        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            val dialogWindow = inflater.inflate(layoutId, null)

            // Fill in input field with item's text to be edited
            dialogWindow.findViewById<EditText>(editFieldId).setText(itemsViewModel.customItems.value?.get(index))

            builder.setView(dialogWindow)
                .setTitle("Редактировать")
                .setPositiveButton("Принять") { _, _ ->
                    val input = dialogWindow.findViewById<EditText>(editFieldId)
                    if (input.text.toString().isNotEmpty()) {
                        itemsViewModel.addItem(input.text.toString(), index)
                    }
                }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}