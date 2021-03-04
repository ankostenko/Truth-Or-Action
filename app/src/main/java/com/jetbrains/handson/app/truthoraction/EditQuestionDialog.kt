package com.jetbrains.handson.app.truthoraction

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels

class EditQuestionDialog(private val index: Int) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sharedQuestionsViewModel: QuestionsViewModel by activityViewModels()

        return activity?.let {
            val inflater = requireActivity().layoutInflater
            val builder = AlertDialog.Builder(it)
            val dialogWindow = inflater.inflate(R.layout.edit_question_dialog, null)

            // Fill in input field with question to be edited
            dialogWindow.findViewById<EditText>(R.id.edit_question_field).setText(sharedQuestionsViewModel.customQuestions.value?.get(index))

            builder.setView(dialogWindow)
                .setTitle("Редактировать")
                .setPositiveButton("Принять", DialogInterface.OnClickListener {
                        _, _ ->
                    val input = dialogWindow.findViewById<EditText>(R.id.edit_question_field)
                    if (input.text.toString().isNotEmpty()) {
                        sharedQuestionsViewModel.addQuestion(input.text.toString(), index)
                    }
                })
                .setNegativeButton("Отмена", DialogInterface.OnClickListener { dialog, _ -> dialog.cancel() })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }
}