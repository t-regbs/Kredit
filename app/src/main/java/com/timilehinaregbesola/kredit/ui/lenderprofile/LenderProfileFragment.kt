package com.timilehinaregbesola.kredit.ui.lenderprofile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timilehinaregbesola.kredit.HomeActivity
import com.timilehinaregbesola.kredit.MainActivity
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.databinding.FragmentLenderProfileBinding

class LenderProfileFragment : Fragment() {

    private lateinit var binding: FragmentLenderProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLenderProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtLogout.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }
}