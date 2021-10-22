package com.quiz.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.fatme.hyundai.callbacks.AdapterViewClickListener
import com.fatme.hyundai.callbacks.BaseListAdapterDiffCallBack
import com.quiz.model.models.QuestionSummaryModel
import kotlinx.android.synthetic.main.row_answer_list.view.*
import quiz.R

class QuestionSummaryAdapter(
    private val adapterViewClickListener: AdapterViewClickListener<QuestionSummaryModel>?
) : ListAdapter<QuestionSummaryModel, QuestionSummaryAdapter.ViewHolder>(
    BaseListAdapterDiffCallBack<QuestionSummaryModel>()
) {

    inner class ViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bind(
                result: QuestionSummaryModel?,
                itemViewType: Int,
                adapterViewClickListener: AdapterViewClickListener<QuestionSummaryModel>?
        ) {
            setDataOnAdapterView(itemView, result, adapterViewClickListener, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val layoutId = R.layout.row_answer_list
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), getItemViewType(position), adapterViewClickListener)
    }

    fun setDataOnAdapterView(
            itemView: View,
            result: QuestionSummaryModel?,
            adapterViewClickListener: AdapterViewClickListener<QuestionSummaryModel>?,
            adapterPosition: Int
    ) {
        itemView.tvQuestion.text = result?.question
        itemView.tvCorrectAnswer.text = result?.correctAnswer
        itemView.tvGivenAnswer.text = result?.givenAnswer

        if (result?.isCorrect == true){
            itemView.tvYesNo.text = itemView.context.getString(R.string.yes)
            itemView.tvYesNo.setTextColor(ContextCompat.getColor(itemView.context, R.color.green));
        }else{
            itemView.tvYesNo.text = itemView.context.getString(R.string.no)
            itemView.tvYesNo.setTextColor(ContextCompat.getColor(itemView.context, R.color.red));
        }

    }
}