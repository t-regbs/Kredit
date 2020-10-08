package com.timilehinaregbesola.kredit.ui.lenderstats

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.data.model.Loan
import com.timilehinaregbesola.kredit.data.model.SterlingResult
import com.timilehinaregbesola.kredit.data.network.ResultRetriever
import com.timilehinaregbesola.kredit.databinding.FragmentLenderStatsBinding
import com.timilehinaregbesola.kredit.ui.stats.LoanAdapter
import com.timilehinaregbesola.kredit.ui.stats.StatsFragmentDirections
import com.timilehinaregbesola.kredit.ui.stats.StatsViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LenderStatsFragment : Fragment() {

    private lateinit var statsViewModel: StatsViewModel
    private lateinit var binding: FragmentLenderStatsBinding
    private lateinit var requestAdapter: LoanAdapter
    private lateinit var offersAdapter: LoanAdapter

    private val retriever = ResultRetriever()

    private val callback = object : Callback<SterlingResult> {
        override fun onFailure(call: Call<SterlingResult>?, t:Throwable?) {
            Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
        }

        override fun onResponse(call: Call<SterlingResult>?, response: Response<SterlingResult>?) {
            response?.isSuccessful.let {

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statsViewModel =
            ViewModelProvider(this).get(StatsViewModel::class.java)
        requestAdapter = LoanAdapter (LoanAdapter.LoanListener { loan ->
            val dialogView: View = layoutInflater.inflate(R.layout.dialog_loan_clicked, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)
            val purpose = dialog.findViewById<TextView>(R.id.loan_purpose)
            purpose?.setText(loan.type)
            val txtInterest = dialog.findViewById<TextView>(R.id.txt_interest)
            txtInterest?.setText(loan.interest)
            val txtTime = dialog.findViewById<TextView>(R.id.txt_payback_time)
            txtTime?.setText(loan.date)
            dialog.show()


            val btnApprove = dialog.findViewById<MaterialButton>(R.id.btn_approve_loan)
            val btnDecline = dialog.findViewById<MaterialButton>(R.id.btn_decline_loan)
            btnApprove?.setOnClickListener {
                binding.progress.visibility = View.VISIBLE
                dialog.dismiss()
                if (isNetworkConnected()) {
                    retrieveTransferResult()
                    binding.progress.visibility = View.GONE
                    dialog.dismiss()
                } else {
                    binding.progress.visibility = View.GONE
                    AlertDialog.Builder(requireContext()).setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
            }
            btnDecline?.setOnClickListener {
                dialog.dismiss()
            }

        })

        offersAdapter = LoanAdapter(LoanAdapter.LoanListener { loan ->
            val dialogView: View =
                layoutInflater.inflate(R.layout.dialog_loan_clicked_no_button, null)
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

        binding = FragmentLenderStatsBinding.inflate(inflater, container, false).apply {
            rvOffers.adapter = offersAdapter
            rvRequests.adapter = requestAdapter
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestAdapter.submitList(createDummyLoans())

        statsViewModel.loanList.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()){
                binding.txtEmpty.visibility = View.GONE
                it.let{ offersAdapter.submitList(it) }
            } else {
                binding.txtEmpty.visibility = View.VISIBLE
            }
        })

        binding.btnAddLoan.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.loan_offer_dialog, null)
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

    private fun retrieveTransferResult() {
        val fragmentJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(requireContext()).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }

        //3 the Coroutine runs using the Main (UI) dispatcher
        val coroutineScope = CoroutineScope(fragmentJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            //4
            val result = ResultRetriever().getResult()
            Log.d("Payment", result.toString())
            if (result.data.status == "00") {
                AlertDialog.Builder(requireContext()).setTitle("Success!!")
                    .setMessage("Payment has been successful")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(R.drawable.ic_baseline_check_circle_outline_24).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = requireContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
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