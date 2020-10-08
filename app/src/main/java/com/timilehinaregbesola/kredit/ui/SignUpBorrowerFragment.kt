package com.timilehinaregbesola.kredit.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.timilehinaregbesola.kredit.data.network.ResultRetriever
import com.timilehinaregbesola.kredit.databinding.FragmentSignUpBorrowerBinding
import kotlinx.coroutines.*

class SignUpBorrowerFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBorrowerBinding
    private val safeArgs: SignUpBorrowerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBorrowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            val prefix = "+234"
            val number = binding.edtPhone.editText?.text.toString().trim()
            val fullNumber = prefix + number
            if (number.isBlank() || fullNumber.length < 14) {
                Toast.makeText(requireContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            } else {
                if (isNetworkConnected()) {
                    retrieveTransferResult(fullNumber)
                    findNavController().navigate(SignUpBorrowerFragmentDirections.actionSignUpBorrowerFragmentToBorrowerOtpFragment(safeArgs.userType))
                } else {
                    AlertDialog.Builder(requireContext()).setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again")
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
            }

        }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun retrieveTransferResult(fullNumber: String) {
        val fragmentJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("SignUpBorrower",exception.message!!)
            Toast.makeText(requireContext(), "Error sending OTP", Toast.LENGTH_SHORT).show()
        }

        val coroutineScope = CoroutineScope(fragmentJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            //4
            val result = ResultRetriever().getOtpResult(fullNumber)
            if (result.SMSMessageData.Recipients[0].statusCode == 101) {
               Toast.makeText(requireContext(), "OTP was sent successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}