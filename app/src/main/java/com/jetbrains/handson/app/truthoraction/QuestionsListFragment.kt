package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class QuestionsListFragment : Fragment() {
    private val sharedQuestionList: QuestionsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.questions_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = QuestionList(sharedQuestionList, requireActivity().supportFragmentManager)
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

        sharedQuestionList.customQuestions.observe(viewLifecycleOwner, {
            recyclerView.adapter = QuestionList(sharedQuestionList, requireActivity().supportFragmentManager)

            if (sharedQuestionList.customQuestions.value?.isEmpty() == false) {
                view.findViewById<TextView>(R.id.empty_questions_list_text_view).visibility = View.INVISIBLE
            } else {
                view.findViewById<TextView>(R.id.empty_questions_list_text_view).visibility = View.VISIBLE
            }
        })
    }
}

class QuestionList(private val list: QuestionsViewModel, private val supportFragmentManager: FragmentManager) : RecyclerView.Adapter<QuestionList.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_question_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list.customQuestions.value?.get(position)
        holder.itemView.findViewById<ImageButton>(R.id.item_question_dots_menu).setOnClickListener {
            val popup = PopupMenu(it.context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.edit_delete_list_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.edit_button -> {
                        val dialog = EditQuestionDialog(position)
                        dialog.show(supportFragmentManager, "edit_question")
                        true
                    }
                    R.id.delete_button -> {
                        list.removeQuestion(position)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun getItemCount(): Int = list.customQuestions.value?.size?:0
}