package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class QuestionsListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.questions_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedQuestionList: QuestionsViewModel by activityViewModels()

        val adapter = QuestionList(sharedQuestionList.availableQuestions.value?: mutableListOf("[Error]: Could't extract questions"))
        val recyclerView: RecyclerView = view.findViewById(R.id.questions_list)
        recyclerView.adapter = adapter

        val toolbar: Toolbar = view.findViewById(R.id.question_list_toolbar)
        toolbar.title = "Вопросы"
        toolbar.setTitleTextColor(0x023047)
        toolbar.setTitleTextAppearance(view.context, R.style.QuestionListToolbarTitle)
        toolbar.inflateMenu(R.menu.questions_list_menu)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.questions_list_to_menu)
        }
    }
}

class QuestionList(private val list: MutableList<String>) : RecyclerView.Adapter<QuestionList.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_question_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position]
    }

    override fun getItemCount(): Int = list.size
}