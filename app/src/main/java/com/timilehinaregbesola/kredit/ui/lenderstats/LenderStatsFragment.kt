package com.timilehinaregbesola.kredit.ui.lenderstats

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
import com.timilehinaregbesola.kredit.databinding.FragmentLenderStatsBinding
import com.timilehinaregbesola.kredit.ui.stats.LoanAdapter
import com.timilehinaregbesola.kredit.ui.stats.StatsFragmentDirections
import com.timilehinaregbesola.kredit.ui.stats.StatsViewModel
import java.util.*

class LenderStatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var binding: FragmentLenderStatsBinding
    private val adapter = LoanAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statsViewModel =
            ViewModelProvider(this).get(StatsViewModel::class.java)
        binding = FragmentLenderStatsBinding.inflate(inflater, container, false).apply {
            rvLoans.adapter = adapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.submitList(createDummyLoans())

        binding.btnSearchLoans.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.loan_settings, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)
            dialog.show()

            val btnSearch = dialog.findViewById<MaterialButton>(R.id.btn_save)
            val minCreditScoreDropdown = dialog.findViewById<AutoCompleteTextView>(R.id.min_credit_score_dropdown)
            val minRateDropdown = dialog.findViewById<AutoCompleteTextView>(R.id.min_interest_rate_dropdown)
            val edtMinLoanAmount = dialog.findViewById<TextInputLayout>(R.id.edt_min_amount)
            val minPayBackTime = dialog.findViewById<TextInputLayout>(R.id.edt_min_payback)

            val minCreditArray = arrayOf("790", "700")
            val minCreditAdapter = ArrayAdapter(
                requireContext(),
                R.layout.custom_simple_spinner_item,
                minCreditArray
            )
            minCreditScoreDropdown?.setAdapter(minCreditAdapter)

            val minRateArray = arrayOf("10000", "15000")
            val minRateAdapter = ArrayAdapter(
                requireContext(),
                R.layout.custom_simple_spinner_item,
                minRateArray
            )
            minRateDropdown?.setAdapter(minRateAdapter)

            minCreditScoreDropdown?.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val type = parent?.getItemAtPosition(position).toString()
                }
            minRateDropdown?.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val type = parent?.getItemAtPosition(position).toString()
                }
            btnSearch?.setOnClickListener {
//                findNavController().navigate(StatsFragmentDirections.actionNavigationStatsToLoanSearchResultFragment())
                dialog.dismiss()
            }
        }
    }

    private fun createDummyLoans(): List<Loan> {
        val loan1 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "Education loans with up to 10% interest and flexible payback rates.")
        val loan2 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "Education loans with up to 10% interest and flexible payback rates.")
        val loan3 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "Education loans with up to 10% interest and flexible payback rates.")
        val loan4 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "Education loans with up to 10% interest and flexible payback rates.")
        val loan5 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "Education loans with up to 10% interest and flexible payback rates.")
        val loan6 = Loan(UUID.randomUUID().toString(), "Educational Loan", "5 weeks ago", "Education loans with up to 10% interest and flexible payback rates.")
        return listOf(loan1, loan2, loan3, loan4, loan5, loan6)
    }
}