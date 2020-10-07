package com.timilehinaregbesola.kredit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.timilehinaregbesola.kredit.ui.SplashFragmentDirections
import com.timilehinaregbesola.kredit.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }
        binding.btnSignup.setOnClickListener {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToPreferredLanguageFragment())
        }
    }
}