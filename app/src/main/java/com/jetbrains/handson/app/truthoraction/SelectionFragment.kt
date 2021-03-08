package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SelectionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.game_to_menu)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.truth_button).setOnClickListener {
            findNavController().navigate(R.id.selection_to_truth)
        }

        view.findViewById<Button>(R.id.action_button).setOnClickListener {
            findNavController().navigate(R.id.selection_to_action)
        }

        view.findViewById<Button>(R.id.close_button).setOnClickListener {
            findNavController().navigate(R.id.game_to_menu)
        }
    }
}