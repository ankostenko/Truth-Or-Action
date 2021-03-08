package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jetbrains.handson.app.truthoraction.viewmodels.ActionsViewModel

class ActionFragment : Fragment() {
    private val sharedActionsViewModel: ActionsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_to_menu)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.action_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.close_button).setOnClickListener {
            findNavController().navigate(R.id.action_to_menu)
        }

        view.findViewById<Button>(R.id.next_button).setOnClickListener {
            findNavController().navigate(R.id.action_to_selection)
        }
    }

    override fun onStart() {
        super.onStart()

        // Set new action to the action text
        view?.findViewById<TextView>(R.id.action_text)?.text = sharedActionsViewModel.chooseRandomAction()
    }
}