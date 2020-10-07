package com.timilehinaregbesola.kredit.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.databinding.FragmentCreditScoreTestBinding
import com.timilehinaregbesola.kredit.databinding.FragmentPsycTest1Binding

class CreditScoreTestFragment : Fragment() {
    private lateinit var binding: FragmentCreditScoreTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreditScoreTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(CreditScoreTestFragmentDirections.actionCreditScoreTestFragmentToPsycTest1Fragment())
        }
    }
}