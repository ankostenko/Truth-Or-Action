package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.loader.app.LoaderManager
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

        val adapter = QuestionList(sharedQuestionList.customQuestions.value?: mutableListOf("[Error]: Could't extract questions"), )
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

        // Open dialog window to add question
        toolbar.findViewById<ActionMenuItemView>(R.id.add_question_button).setOnClickListener {
            val dialog = AddQuestionsDialog()
            dialog.show(requireActivity().supportFragmentManager, "add_question")
        }

        if (sharedQuestionList.customQuestions.value?.isEmpty() == false) {
            view.findViewById<TextView>(R.id.empty_questions_list_text_view).visibility = View.INVISIBLE
        } else {
            view.findViewById<TextView>(R.id.empty_questions_list_text_view).visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_delete_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit_button -> {
                println("Edit button clicked")
                true
            }
            R.id.delete_button -> {
                println("Delete button clicked")
                true
            }
            else -> super.onOptionsItemSelected(item)
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
        holder.itemView.findViewById<ImageButton>(R.id.item_question_dots_menu).setOnClickListener {
            val popup = PopupMenu(it.context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.edit_delete_list_menu, popup.menu)
            popup.show()
        }
    }

    override fun getItemCount(): Int = list.size
}