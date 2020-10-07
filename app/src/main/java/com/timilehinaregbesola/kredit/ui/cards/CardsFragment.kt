package com.timilehinaregbesola.kredit.ui.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.timilehinaregbesola.kredit.R
import com.timilehinaregbesola.kredit.data.model.Card
import com.timilehinaregbesola.kredit.databinding.FragmentCardsBinding
import kotlinx.android.synthetic.main.fragment_add_card.view.*
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit


class CardsFragment : Fragment() {

    private lateinit var cardsViewModel: CardsViewModel
    private lateinit var binding: FragmentCardsBinding
    private val adapter = CardAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardsViewModel = ViewModelProvider(this).get(CardsViewModel::class.java)
        binding = FragmentCardsBinding.inflate(inflater, container, false).apply {
            rvCards.adapter = adapter
        }
        cardsViewModel.cardList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardsViewModel.createFakeCards()
        binding.btnAddCard.setOnClickListener {
            val dialogView: View = layoutInflater.inflate(R.layout.fragment_add_card, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)
            dialog.show()

            val addCard = dialog.findViewById<MaterialButton>(R.id.btn_add_card)
            val expiryDate = dialog.findViewById<TextInputLayout>(R.id.edt_expiry_date)
            val cardNum = dialog.findViewById<EditCredit>(R.id.edt_card)
            addCard?.setOnClickListener {
                cardsViewModel.addNewCard(cardNum?.text.toString(), expiryDate?.editText?.text.toString())
                dialog.dismiss()
            }
        }
    }


}