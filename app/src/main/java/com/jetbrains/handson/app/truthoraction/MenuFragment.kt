package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.play_button).setOnClickListener {
            // Restore available questions
            val sharedQuestionsViewModel: QuestionsViewModel by activityViewModels()
            sharedQuestionsViewModel.reinitializeAvailableQuestions()

            val sharedActionsViewModel: ActionsViewModel by activityViewModels()
            sharedActionsViewModel.reinitializeAvailableActions()

            findNavController().navigate(R.id.menu_to_game);
        }

        view.findViewById<Button>(R.id.questions_button).setOnClickListener {
            findNavController().navigate(R.id.menu_question_list)
        }
    }
}