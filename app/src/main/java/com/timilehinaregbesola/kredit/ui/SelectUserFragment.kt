package com.timilehinaregbesola.kredit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.timilehinaregbesola.kredit.ui.SelectUserFragmentDirections
import com.timilehinaregbesola.kredit.databinding.FragmentSelectUserBinding

class SelectUserFragment : Fragment() {

    private lateinit var binding: FragmentSelectUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardLender.setOnClickListener {
            switchOff(binding.cardBorrower)
            switchOn(binding.cardLender)
            binding.cardLender.isChecked = !binding.cardLender.isChecked
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        binding.cardBorrower.setOnClickListener {
            switchOff(binding.cardLender)
            switchOn(binding.cardBorrower)
            binding.cardBorrower.isChecked = !binding.cardBorrower.isChecked
        }

        binding.btnNext.setOnClickListener {
            val type = if (binding.cardLender.isChecked) "Lender" else "Borrower"
            findNavController().navigate(SelectUserFragmentDirections.actionSelectUserFragmentToSignUpBorrowerFragment(type))
        }
    }

    private fun switchOff(card: MaterialCardView) {
        card.isChecked = false
        card.isClickable = false
        card.isCheckable = false
        card.isFocusable = false
    }

    private fun switchOn(card: MaterialCardView) {
        card.isClickable = true
        card.isCheckable = true
        card.isFocusable = true
    }
}