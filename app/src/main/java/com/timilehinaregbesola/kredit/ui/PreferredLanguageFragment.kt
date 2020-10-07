package com.timilehinaregbesola.kredit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.timilehinaregbesola.kredit.ui.PreferredLanguageFragmentDirections
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.databinding.FragmentPreferredLanguageBinding

class PreferredLanguageFragment : Fragment() {

    private lateinit var binding: FragmentPreferredLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreferredLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val type = arrayOf("English", "Yoruba", "Hausa", "Igbo")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.custom_simple_spinner_item,
            type
        )
        binding.outlinedExposedDropdown.setAdapter(adapter)
        binding.outlinedExposedDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, id ->
//                val type = parent?.getItemAtPosition(position).toString()
//                loginViewModel.userType.value = type
//                loginViewModel.userId.value = "1"
//                Timber.d("userType: $type")
            }
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnNext.setOnClickListener {
            findNavController().navigate(PreferredLanguageFragmentDirections.actionPreferredLanguageFragmentToSelectUserFragment())
        }


    }
}