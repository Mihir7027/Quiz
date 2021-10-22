package com.quiz.model.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionSummaryModel(
    val question: String,
    val givenAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean
): Parcelable