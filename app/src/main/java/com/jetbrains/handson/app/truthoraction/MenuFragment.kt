package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jetbrains.handson.app.truthoraction.viewmodels.ActionsViewModel
import com.jetbrains.handson.app.truthoraction.viewmodels.QuestionsViewModel
import kotlin.system.exitProcess

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // On back button pressed application finishes activities
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finishAffinity()
        }
    }

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

            findNavController().navigate(R.id.menu_to_game)
        }

        view.findViewById<Button>(R.id.questions_button).setOnClickListener {
            findNavController().navigate(R.id.menu_to_questions_list)
        }

        view.findViewById<Button>(R.id.actions_button).setOnClickListener {
            findNavController().navigate(R.id.menu_to_action_fragment)
        }
    }
}