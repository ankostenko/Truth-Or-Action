package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class TruthFragment : Fragment() {
    private val sharedQuestionsViewModel: QuestionsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.truth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.close_button).setOnClickListener {
            findNavController().navigate(R.id.action_to_menu)
        }

        view.findViewById<Button>(R.id.next_button).setOnClickListener {
            findNavController().navigate(R.id.truth_to_selection)
        }
    }

    override fun onStart() {
        super.onStart()

        // Set new question to the question text view
        view?.findViewById<TextView>(R.id.question_text)?.text = sharedQuestionsViewModel.chooseRandomQuestion()
    }
}