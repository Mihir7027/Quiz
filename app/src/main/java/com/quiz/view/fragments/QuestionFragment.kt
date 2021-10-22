package com.quiz.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.quiz.utils.AndroidUtils.Companion.showAlertDialog
import com.quiz.utils.AndroidUtils.Companion.twoDigitAfterDecimal
import java.util.*
import android.os.CountDownTimer
import android.os.Parcelable
import android.util.Log
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.question_fragment.*
import com.quiz.model.models.QuestionSummaryModel
import quiz.R
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import java.io.Serializable





class QuestionFragment : Fragment(R.layout.question_fragment), View.OnClickListener {

    private var questionAnswerList: ArrayList<QuestionSummaryModel> = ArrayList()

    private var totalQuestion: Int = 15
    private var completedQuestion: Int = 1
    private var operator: String = ""
    private var firstNumber: Double = 0.0
    private var secondNumber: Double = 0.0

    private var selectedAnswer: Double? = 0.0

    private var correctAnswer: Double? = 0.0
    private var randomAnswerOne: Double = 0.0
    private var randomAnswerTwo: Double = 0.0
    private var randomAnswerThree: Double = 0.0

    lateinit var countDownTimer: CountDownTimer
    var isRunning: Boolean = false;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        init()
    }

    private fun init() {
        tvQuestionTotalCount.text = totalQuestion.toString()
        tvQuestionCount.text = completedQuestion.toString()
        generateQuestionAndCorrectAnswer()
        setAnswer()
        startTimer()

        tvQuestion.text =
            getString(R.string.find_the_correct_solution_for ) + " "+firstNumber.toString() + " " + operator + " " + secondNumber.toString()
        //+ "    ---" + correctAnswer
    }

    private fun generateRandomOperator(): String {
        var operator = ""
        val min = 1
        val max = 5
        val random: Int = Random().nextInt(max - min + 1) + min
        if (random == 1) {
            operator = "+"
        } else if (random == 2) {
            operator = "-"
        } else if (random == 3) {
            operator = "/"
        } else if (random == 4) {
            operator = "*"
        } else if (random == 5) {
            operator = "%"
        }
        return operator
    }

    private fun generateRandomNumberOne(): Double {
        val min = 1
        val max = 500
        return (Random().nextInt(max - min + 1) + min).toDouble()
    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btnNext -> {
                if (isValid()) {

                    setQandAList()
                    completedQuestion++
                    if (completedQuestion == totalQuestion) {
                        btnNext.visibility = View.GONE
                        btnSubmit.visibility = View.VISIBLE
                    }
                    tvQuestionCount.text = completedQuestion.toString()
                    checkAnswer()
                       } else {
                    showAlertDialog(requireActivity(), getString(R.string.select_any_one_answer))
                }
            }

            R.id.btnSubmit -> {
                isValid()
                setQandAList()
                val args = Bundle()
                args.putParcelableArrayList("answerList", questionAnswerList)
                findNavController().navigate(R.id.action_listFragment_to_detailFragment,args)
            }
        }
    }

    private fun setQandAList(){
        val isAnswerRight = correctAnswer == selectedAnswer
        var selectedAns = ""
        if (selectedAnswer == 0.0){
            selectedAns = "N/A"
        }else{
            selectedAns = selectedAnswer.toString()
        }

        val questionSummaryModel = QuestionSummaryModel(tvQuestion.text.toString().trim(),selectedAns,correctAnswer.toString(),isAnswerRight)
        questionAnswerList.add(questionSummaryModel)
    }

    private fun setAnswer() {
        var answerArray: ArrayList<Double> = ArrayList()
        var shuffledArray: List<Double> = ArrayList()
        answerArray.add(correctAnswer!!)
        answerArray.add(randomAnswerOne)
        answerArray.add(randomAnswerTwo)
        answerArray.add(randomAnswerThree)
        shuffledArray = answerArray.shuffled()
        rbAnswerOne.text = shuffledArray[0].toString()
        rbAnswerTwo.text = shuffledArray[1].toString()
        rbAnswerThree.text = shuffledArray[2].toString()
        rbAnswerFour.text = shuffledArray[3].toString()
    }

    private fun generateQuestionAndCorrectAnswer() {
        firstNumber = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()
        secondNumber = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()
        operator = generateRandomOperator()
        correctAnswer = twoDigitAfterDecimal(calculateAnswer()).toDouble()

        randomAnswerOne = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()
        randomAnswerTwo = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()
        randomAnswerThree = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()

        if (correctAnswer == randomAnswerOne) {
            randomAnswerOne = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()
        }

        if (correctAnswer == randomAnswerTwo) {
            randomAnswerTwo = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()
        }

        if (correctAnswer == randomAnswerThree) {
            randomAnswerThree = twoDigitAfterDecimal(generateRandomNumberOne()).toDouble()
        }
        setAnswer()
        tvQuestion.text =  getString(R.string.find_the_correct_solution_for )  + " "+firstNumber.toString() + " " + operator + " " + secondNumber.toString()
        //+ "    ---" + correctAnswer

    }

    private fun calculateAnswer(): Double {
        val str: String = operator.replace(" ", "")
        var answer: Double = 0.0
        if (str == "*") {
            answer = firstNumber * secondNumber
        } else if (str == "+") {
            answer = firstNumber + secondNumber
        } else if (str == "-") {
            answer = firstNumber - secondNumber
        } else if (str == "/") {
            answer = firstNumber / secondNumber
        } else if (str == "%") {
            answer = firstNumber % secondNumber
        }
        return answer
    }

    private fun checkAnswer() {
        if (rbAnswerOne.isChecked) {
            selectedAnswer = rbAnswerOne.text.toString().trim().toDouble()
        } else if (rbAnswerTwo.isChecked) {
            selectedAnswer = rbAnswerTwo.text.toString().trim().toDouble()
        } else if (rbAnswerThree.isChecked) {
            selectedAnswer = rbAnswerThree.text.toString().trim().toDouble()
        } else if (rbAnswerFour.isChecked) {
            selectedAnswer = rbAnswerFour.text.toString().trim().toDouble()
        }

        radioGroup.clearCheck()
        selectedAnswer = 0.0
        startTimer()
        generateQuestionAndCorrectAnswer()

    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    override fun onStop() {
        super.onStop()
        countDownTimer.cancel()
    }

    private fun startTimer() {
        if (isRunning){
            countDownTimer.cancel()
        }
        countDownTimer = object : CountDownTimer(31000, 1000) {
            override fun onFinish() {
                if (completedQuestion <15){
                    val isAnswerRight = correctAnswer == selectedAnswer
                    var selectedAns:String = "N/A"
                    if (selectedAnswer != 0.0 ){
                        selectedAns = selectedAnswer.toString()
                    }
                    val questionSummaryModel = QuestionSummaryModel(tvQuestion.text.toString().trim(),selectedAns.toString(),correctAnswer.toString(),isAnswerRight)
                    questionAnswerList.add(questionSummaryModel)

                    completedQuestion++

                    if (completedQuestion == totalQuestion) {
                        btnNext.visibility = View.GONE
                        btnSubmit.visibility = View.VISIBLE
                    }
                    tvQuestionCount.text = completedQuestion.toString()
                    checkAnswer()
                    generateQuestionAndCorrectAnswer()
                    setAnswer()
                    tvQuestion.text = getString(R.string.find_the_correct_solution_for ) + " " +firstNumber.toString() + " " + operator + " " + secondNumber.toString()
                    //+ "    ---" + correctAnswer
                }else if (completedQuestion == 15){
                    isValid()
                    setQandAList()
                    val args = Bundle()
                    args.putParcelableArrayList("answerList", questionAnswerList)
                    findNavController().navigate(R.id.action_listFragment_to_detailFragment,args)
                }

            }

            override fun onTick(millis: Long) {
                val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millis)
                if (tvTime != null){
                    tvTime.text = seconds.toString()

                }
            }
        }
        countDownTimer.start()
        isRunning = true
    }


    private fun isValid(): Boolean {
        var isValid: Boolean = false
        if (rbAnswerOne.isChecked) {
            selectedAnswer = rbAnswerOne.text.toString().toDouble()
            isValid = true
        }else if (rbAnswerTwo.isChecked){
            selectedAnswer = rbAnswerTwo.text.toString().toDouble()
            isValid = true
        }else if (rbAnswerThree.isChecked){
            selectedAnswer = rbAnswerThree.text.toString().toDouble()
            isValid = true
        }else if (rbAnswerFour.isChecked){
            selectedAnswer = rbAnswerFour.text.toString().toDouble()
            isValid = true
        }else{
            selectedAnswer = 0.0
            isValid = false
        }
        return isValid
    }
}
