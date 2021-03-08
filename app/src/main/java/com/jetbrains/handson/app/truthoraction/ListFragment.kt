package com.jetbrains.handson.app.truthoraction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

open class ListFragment(private val questionViewModel: Boolean): Fragment() {
    private val sharedItemsList: ItemViewModel = if (questionViewModel) {
        QuestionsViewModel(requireActivity().application)
    } else {
        ActionsViewModel(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewerList(sharedItemsList, requireActivity().supportFragmentManager)
        val recyclerView: RecyclerView = view.findViewById(R.id.items_list)
        recyclerView.adapter = adapter

        val toolbar: Toolbar = view.findViewById(R.id.items_list_toolbar)
        // Choose title of a fragment depending on type of a given ViewModel
        if (questionViewModel) {
            toolbar.title = "Вопросы"
        } else {
            toolbar.title = "Действия"
        }
        toolbar.setTitleTextColor(0x023047)
        toolbar.setTitleTextAppearance(view.context, R.style.QuestionListToolbarTitle)
        toolbar.inflateMenu(R.menu.questions_list_menu)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            val backNavigationPath = if (questionViewModel) {
                R.id.questions_list_to_menu
            } else {
                R.id.actions_list_to_menu
            }
            findNavController().navigate(backNavigationPath)
        }

        // Open dialog window to add question
        toolbar.findViewById<ActionMenuItemView>(R.id.add_question_button).setOnClickListener {
            val dialog = AddQuestionsDialog()
            dialog.show(requireActivity().supportFragmentManager, "add_item")
        }

        sharedItemsList.customItems.observe(viewLifecycleOwner, {
            recyclerView.adapter = ViewerList(sharedItemsList, requireActivity().supportFragmentManager)

            val textView = view.findViewById<TextView>(R.id.empty_items_list_text_view)
            if (questionViewModel) {
                textView.text = "Здесь пока нет ваших вопросов"
            } else {
                textView.text = "Здесь пока нет ваших действий"
            }
            if (sharedItemsList.customItems.value?.isEmpty() == false) {
                textView.visibility = View.INVISIBLE
            } else {
                textView.visibility = View.VISIBLE
            }
        })
    }
}

class ViewerList<SharedViewModel: ItemViewModel>(private val list: SharedViewModel, private val supportFragmentManager: FragmentManager): RecyclerView.Adapter<ViewerList.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_question_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list.customItems.value?.get(position)
        holder.itemView.findViewById<ImageButton>(R.id.item_question_dots_menu).setOnClickListener {
            val popup = PopupMenu(it.context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.edit_delete_list_menu, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.edit_button -> {
                        val dialog = EditQuestionDialog(position)
                        dialog.show(supportFragmentManager, "edit_item")
                        true
                    }
                    R.id.delete_button -> {
                        list.removeItem(position)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun getItemCount(): Int = list.customItems.value?.size?:0
}