package com.timilehinaregbesola.kredit.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.data.model.Loan
import com.timilehinaregbesola.kredit.databinding.FragmentStatsBinding
import java.util.*

class StatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var binding: FragmentStatsBinding
    private val adapter = LoanAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statsViewModel =
            ViewModelProvider(this).get(StatsViewModel::class.java)
        binding = FragmentStatsBinding.inflate(inflater, container, false).apply {
            rvLoans.adapter = adapter
        }
//        statsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(createDummyLoans())

        binding.btnSearchLoans.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.search_loans, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)
            dialog.show()

            val btnSearch = dialog.findViewById<MaterialButton>(R.id.btn_search)
            val purposeDropdown = dialog.findViewById<AutoCompleteTextView>(R.id.purpose_dropdown)
            val minRateDropdown = dialog.findViewById<AutoCompleteTextView>(R.id.min_interest_rate_dropdown)
            val edtAmount = dialog.findViewById<TextInputLayout>(R.id.edt_amount)
            val minPayBackTime = dialog.findViewById<TextInputLayout>(R.id.edt_min_payback)

            val purposeArray = arrayOf("Educational", "Business")
            val purposeAdapter = ArrayAdapter(
                requireContext(),
                R.layout.custom_simple_spinner_item,
                purposeArray
            )
            purposeDropdown?.setAdapter(purposeAdapter)

            val minRateArray = arrayOf("10000", "15000")
            val minRateAdapter = ArrayAdapter(
                requireContext(),
                R.layout.custom_simple_spinner_item,
                minRateArray
            )
            minRateDropdown?.setAdapter(minRateAdapter)

            purposeDropdown?.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val type = parent?.getItemAtPosition(position).toString()
                }
            minRateDropdown?.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val type = parent?.getItemAtPosition(position).toString()
                }
            btnSearch?.setOnClickListener {
                findNavController().navigate(StatsFragmentDirections.actionNavigationStatsToLoanSearchResultFragment())
                dialog.dismiss()
            }
        }
    }

    private fun createDummyLoans(): List<Loan> {
        val loan1 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "You just started a new cycle, time to grow new plants")
        val loan2 = Loan(UUID.randomUUID().toString(), "Business Loan", "1 week ago" )
        val loan3 = Loan(UUID.randomUUID().toString(), "Business Loan", "5 days ago")
        val loan4 = Loan(UUID.randomUUID().toString(), "Business Loan", "2 days ago")
        return listOf(loan1, loan2, loan3, loan4)
    }
}