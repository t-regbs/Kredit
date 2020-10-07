package com.timilehinaregbesola.kredit.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.databinding.FragmentPsycTest1Binding
import com.timilehinaregbesola.kredit.databinding.FragmentPsycTest2Binding

class PsycTest2Fragment : Fragment() {

    private lateinit var binding: FragmentPsycTest2Binding
    private var isBtnYes: Boolean? = null
    private var answer = "-------"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPsycTest2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toggleButton.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (checkedId) {
                R.id.btn_yes -> {
                    isBtnYes = true
                }
                R.id.btn_no -> {
                    isBtnYes = false
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnNext.setOnClickListener {
            if (isBtnYes == null) {
                Toast.makeText(requireContext(), "Make a choice!!", Toast.LENGTH_SHORT).show()
            } else {
                if (isBtnYes!!) {
                    answer.replaceFirst("-", "Y")
                } else {
                    answer.replaceFirst("-", "N")
                }
                findNavController().navigate(PsycTest1FragmentDirections.actionPsycTest1FragmentToPsycTest2Fragment())
            }
        }
    }
}