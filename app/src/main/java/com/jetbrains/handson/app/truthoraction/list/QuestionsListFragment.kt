package com.jetbrains.handson.app.truthoraction.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.app.truthoraction.R
import com.jetbrains.handson.app.truthoraction.additems.AddItemDialog
import com.jetbrains.handson.app.truthoraction.viewmodels.QuestionsViewModel

open class QuestionsListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pick VM based on which VM needed
        val questionsViewModel: QuestionsViewModel by activityViewModels()

        val adapter = ViewerList(questionsViewModel, requireActivity().supportFragmentManager)
        val recyclerView: RecyclerView = view.findViewById(R.id.items_list)
        recyclerView.adapter = adapter

        val toolbar: Toolbar = view.findViewById(R.id.items_list_toolbar)
        // Choose title of a fragment depending on type of a given ViewModel
        toolbar.title = "Вопросы"
        toolbar.setTitleTextColor(0x023047)
        toolbar.setTitleTextAppearance(view.context, R.style.QuestionListToolbarTitle)
        toolbar.inflateMenu(R.menu.questions_list_menu)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.questions_list_to_menu)
        }

        // Open dialog window to add question
        toolbar.findViewById<ActionMenuItemView>(R.id.add_question_button).setOnClickListener {
            val dialog = AddItemDialog("вопрос", questionsViewModel)
            dialog.show(requireActivity().supportFragmentManager, "add_item")
        }

        questionsViewModel.customItems.observe(viewLifecycleOwner, {
            recyclerView.adapter = ViewerList(questionsViewModel, requireActivity().supportFragmentManager)

            val textView = view.findViewById<TextView>(R.id.empty_items_list_text_view)
            textView.text = "Здесь пока нет ваших вопросов"
            if (questionsViewModel.customItems.value?.isEmpty() == false) {
                textView.visibility = View.INVISIBLE
            } else {
                textView.visibility = View.VISIBLE
            }
        })
    }
}