package com.jetbrains.handson.app.truthoraction

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

class AddQuestionsDialog() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            val dialogWindow = inflater.inflate(R.layout.add_question_dialog, null)
            builder.setView(dialogWindow)
                .setTitle("Добавить вопрос")
                .setPositiveButton("Добавить", DialogInterface.OnClickListener {
                    _, _ ->
                        val input = dialogWindow.findViewById<EditText>(R.id.question_input)
                        val sharedQuestionsViewModel: QuestionsViewModel by activityViewModels()
                        if (input.text.toString().isNotEmpty()) {
                            sharedQuestionsViewModel.addQuestion(input.text.toString())
                        }
                })
                .setNegativeButton("Отмена", DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}