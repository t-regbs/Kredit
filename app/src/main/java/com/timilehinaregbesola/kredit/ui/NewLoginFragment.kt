package com.timilehinaregbesola.kredit.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timilehinaregbesola.kredit.HomeActivity
import com.timilehinaregbesola.kredit.LenderActivity
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.databinding.FragmentLoginFullBinding

class NewLoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginFullBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginFullBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.preferredLanguageFragment)
        }
        binding.btnSignin.setOnClickListener {
            val email = binding.edtEmail.editText?.text.toString().trim()
            val password = binding.edtPassword.editText?.text.toString().trim()

            when {
                email.isBlank() or password.isBlank() -> {
                    Toast.makeText(requireContext(), "Please fill in email or password", Toast.LENGTH_SHORT).show()
                }
                email == "Ciroma" && password == "000000" -> {
                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
                    requireActivity().finish()
                }
                email == "Maria" && password == "123456" -> {
                    startActivity(Intent(requireActivity(), LenderActivity::class.java))
                    requireActivity().finish()
                }
                else -> {
                    Toast.makeText(requireContext(), "Credentials incorrect!! Check email or password", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}