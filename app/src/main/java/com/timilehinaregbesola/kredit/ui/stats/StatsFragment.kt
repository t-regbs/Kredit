package com.timilehinaregbesola.kredit.ui.stats

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.data.model.Loan
import com.timilehinaregbesola.kredit.databinding.FragmentStatsBinding
import com.timilehinaregbesola.kredit.ui.stats.LoanAdapter.LoanListener
import java.util.*

class StatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var binding: FragmentStatsBinding
    private lateinit var requestAdapter: LoanAdapter
    private lateinit var offersAdapter: LoanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statsViewModel =
            ViewModelProvider(this).get(StatsViewModel::class.java)
        requestAdapter = LoanAdapter( LoanListener{ loan ->
            val dialogView: View = layoutInflater.inflate(R.layout.dialog_loan_clicked_no_button, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)
            val purpose = dialog.findViewById<TextView>(R.id.loan_purpose)
            purpose?.setText(loan.type)
            val txtInterest = dialog.findViewById<TextView>(R.id.txt_interest)
            txtInterest?.setText(loan.interest)
            val txtTime = dialog.findViewById<TextView>(R.id.txt_payback_time)
            txtTime?.setText(loan.date)
            dialog.show()
        })
        offersAdapter = LoanAdapter(LoanListener{loan ->
            val dialogView: View = layoutInflater.inflate(R.layout.dialog_loan_clicked_no_button, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)
            val purpose = dialog.findViewById<TextView>(R.id.loan_purpose)
            purpose?.setText(loan.type)
            val txtInterest = dialog.findViewById<TextView>(R.id.txt_interest)
            txtInterest?.setText(loan.interest)
            val txtTime = dialog.findViewById<TextView>(R.id.txt_payback_time)
            txtTime?.setText(loan.date)
            dialog.show()
        })

        binding = FragmentStatsBinding.inflate(inflater, container, false).apply {
            rvOffers.adapter = offersAdapter
            rvRequests.adapter = requestAdapter
        }
//        statsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return binding.root
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        offersAdapter.submitList(statsViewModel.createFakeLoans())
        statsViewModel.loanList.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()){
                binding.txtEmpty.visibility = View.GONE
                it.let{ requestAdapter.submitList(it) }
            } else {
                binding.txtEmpty.visibility = View.VISIBLE
            }
        })

        binding.btnAddLoan.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.loan_request_dialog, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)
            dialog.show()

            val btnSave = dialog.findViewById<MaterialButton>(R.id.btn_create)
            val edtLoanPurpose = dialog.findViewById<TextInputLayout>(R.id.edt_loan_purpose)
            val edtLoanAmount = dialog.findViewById<TextInputLayout>(R.id.edt_loan_amount)
            val minPayBackTime = dialog.findViewById<TextInputLayout>(R.id.edt_min_payback)
            val minRateDropdown = dialog.findViewById<AutoCompleteTextView>(R.id.min_interest_rate_dropdown)

            val minRateArray = arrayOf("0-10", "10-20")
            val minRateAdapter = ArrayAdapter(
                requireContext(),
                R.layout.custom_simple_spinner_item,
                minRateArray
            )
            var minRate: String? = ""
            minRateDropdown?.setAdapter(minRateAdapter)
            minRateDropdown?.setAdapter(minRateAdapter)
            minRateDropdown?.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    minRate = parent?.getItemAtPosition(position).toString()
                }

            btnSave?.setOnClickListener {
                statsViewModel.addNewLoan(
                    type = edtLoanPurpose?.editText?.text.toString(),
                    date = minPayBackTime?.editText?.text.toString(),
                    interest = minRate!!
                )
                dialog.dismiss()
            }

        }

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