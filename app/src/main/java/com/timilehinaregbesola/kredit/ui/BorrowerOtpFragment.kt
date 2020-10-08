package com.timilehinaregbesola.kredit.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.timilehinaregbesola.kredit.HomeActivity
import com.timilehinaregbesola.kredit.LenderActivity
import com.timilehinaregbesola.kredit.databinding.FragmentBorrowerOtpBinding

class BorrowerOtpFragment : Fragment() {
    private lateinit var binding: FragmentBorrowerOtpBinding
    private val safeArgs: SignUpBorrowerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBorrowerOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnVerify.setOnClickListener {
            val pin = binding.pinView.pinResults
            if (pin.isBlank()) {
                Toast.makeText(requireContext(), "Enter OTP", Toast.LENGTH_SHORT).show()
            }
            if (pin == "0000") {
                if (safeArgs.userType == "Borrower") {
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish()
                } else {
                    startActivity(Intent(requireActivity(), LenderActivity::class.java))
                    requireActivity().finish()
                }
            } else {
                Toast.makeText(requireContext(), "Incorrect OTP Entered", Toast.LENGTH_SHORT).show()
            }


        }
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }
}