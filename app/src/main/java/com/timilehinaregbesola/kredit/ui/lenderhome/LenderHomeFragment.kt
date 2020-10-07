package com.timilehinaregbesola.kredit.ui.lenderhome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.data.model.Deal
import com.timilehinaregbesola.kredit.data.model.Transaction
import com.timilehinaregbesola.kredit.databinding.FragmentHomeBinding
import com.timilehinaregbesola.kredit.databinding.FragmentLenderHomeBinding
import com.timilehinaregbesola.kredit.ui.home.HomeDealAdapter
import com.timilehinaregbesola.kredit.ui.home.HomeFragmentDirections
import com.timilehinaregbesola.kredit.ui.home.HomeTransactionAdapter
import com.timilehinaregbesola.kredit.ui.home.HomeViewModel

class LenderHomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentLenderHomeBinding
    private val homeDealAdapter = HomeDealAdapter()
    private val homeTransactionAdapter = HomeTransactionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentLenderHomeBinding.inflate(inflater, container, false).apply {
            rvDeals.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvDeals.adapter = homeDealAdapter
            rvTransaction.adapter = homeTransactionAdapter
            indicator.attachToRecyclerView(rvDeals)
        }
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = createDealData()
        val transList = createTransactionData()
        homeDealAdapter.submitList(list)
        homeTransactionAdapter.submitList(transList)

        binding.cardSmall.setOnClickListener {
//            findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToCreditScoreFragment())
        }
        binding.imgProfile.setOnClickListener {
            findNavController().navigate(R.id.navigation_lend_profile)
        }
    }

    private fun createDealData(): List<Deal> {
        val deal = Deal(1, "Agriculture", "100,000", 5, 60)
        return listOf(deal, deal, deal)
    }

    private fun createTransactionData(): List<Transaction> {
        val trans1 = Transaction(1, "Loan Request", "Approved", "- ₦144.00", "12 Sept 2019")
        val trans2 = Transaction(2, "Loan Request", "Approved", "- ₦124.00", "22 Sept 2019")
        val trans3 = Transaction(3, "Loan Request", "Approved", "- ₦43.50", "10 Sept 2019")
        val trans4 = Transaction(4, "Loan Request", "Approved", "- ₦500.00", "3 Oct 2019")
        return listOf(trans1, trans2, trans3, trans4)
    }
}