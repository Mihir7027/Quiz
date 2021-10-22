package com.quiz.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatme.hyundai.callbacks.AdapterViewClickListener
import com.quiz.model.models.QuestionSummaryModel
import com.quiz.view.adapters.QuestionSummaryAdapter
import com.quiz.viewModel.viewModels.DetailViewModel
import kotlinx.android.synthetic.main.summary_fragment.*
import quiz.R

class SummaryFragment : Fragment(R.layout.summary_fragment) {

    private val viewModel by viewModels<DetailViewModel>()
    private var obtainedMarks:Int  = 0
    private var skippedQuestion:Int  = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    fun init(){
        val answerList = arguments!!.getParcelableArrayList<QuestionSummaryModel>("answerList")

        if (answerList != null && answerList.size > 0) {
            for(i in answerList.indices){
                if (answerList[i].isCorrect){
                    obtainedMarks += 1
                }
                if (answerList[i].givenAnswer == "N/A"){
                    skippedQuestion +=1
                }
            }

            tvObtainedMarks.text = obtainedMarks.toString()
            tvSkipped.text = skippedQuestion.toString()
            tvPercentage.text = ((obtainedMarks*100)/15).toString() + " %"
            setAdapterForPhysicianList(answerList)
        }
    }


    private fun setAdapterForPhysicianList(list: ArrayList<QuestionSummaryModel>) {

        rvQuestionSummary.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        val questionSummaryAdapter = QuestionSummaryAdapter(object :
            AdapterViewClickListener<QuestionSummaryModel> {
            override fun onClickAdapterView(
                objectAtPosition: QuestionSummaryModel?,
                viewType: Int,
                position: Int
            ) {
                when (viewType) {

                }
            }
        })


        rvQuestionSummary.adapter = questionSummaryAdapter
        questionSummaryAdapter.submitList(list)
    }

}
