package com.timilehinaregbesola.kredit.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.data.model.Loan
import com.timilehinaregbesola.kredit.databinding.FragmentLoanSearchResultBinding
import com.timilehinaregbesola.kredit.databinding.FragmentStatsBinding
import java.util.*

class LoanSearchResultFragment : Fragment() {
    private lateinit var binding: FragmentLoanSearchResultBinding
    private val adapter = LoanAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoanSearchResultBinding.inflate(inflater, container, false).apply {
            rvLoans.adapter = adapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(createDummyLoans())
    }

    private fun createDummyLoans(): List<Loan> {
        val loan1 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "You just started a new cycle, time to grow new plants")
        val loan2 = Loan(UUID.randomUUID().toString(), "Business Loan", "1 week ago" )
        val loan3 = Loan(UUID.randomUUID().toString(), "Business Loan", "5 days ago")
        val loan4 = Loan(UUID.randomUUID().toString(), "Business Loan", "2 days ago")
        return listOf(loan1, loan2, loan3, loan4)
    }
}