package com.timilehinaregbesola.kredit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.timilehinaregbesola.kredit.ui.SignUpBorrowerFragmentDirections
import com.timilehinaregbesola.kredit.databinding.FragmentSignUpBorrowerBinding

class SignUpBorrowerFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBorrowerBinding
    private val safeArgs: SignUpBorrowerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBorrowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(SignUpBorrowerFragmentDirections.actionSignUpBorrowerFragmentToBorrowerOtpFragment(safeArgs.userType))
        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }
}