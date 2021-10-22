package com.quiz.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.start_fragment.*
import quiz.R

class StartFragment : Fragment(R.layout.start_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStartQuiz.setOnClickListener { findNavController().navigate(R.id.action_startFragment_to_listFragment) }

    }
}
