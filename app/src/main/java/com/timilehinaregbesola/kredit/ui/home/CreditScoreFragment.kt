package com.timilehinaregbesola.kredit.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.databinding.FragmentCreditScoreBinding

class CreditScoreFragment : Fragment() {

    private lateinit var binding: FragmentCreditScoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreditScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTakeTest.setOnClickListener {
            findNavController().navigate(CreditScoreFragmentDirections.actionCreditScoreFragmentToCreditScoreTestFragment())
        }
    }
}