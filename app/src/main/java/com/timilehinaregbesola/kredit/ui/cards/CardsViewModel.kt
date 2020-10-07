package com.timilehinaregbesola.kredit.ui.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timilehinaregbesola.kredit.data.model.Card
import java.util.*

class CardsViewModel : ViewModel() {

    private val _cardList = MutableLiveData<MutableList<Card>>()
    val cardList: LiveData<MutableList<Card>> = _cardList

    fun createFakeCards() {
        val card1 = Card(UUID.randomUUID().toString(), "5,750,20", "5282 3456 7890 1289", "09/25")
        val card2 = Card(UUID.randomUUID().toString(), "5,750,20", "5282 3456 7890 1289", "09/25")
        _cardList.postValue(mutableListOf(card1, card2))
    }

    fun addNewCard(cardNo: String, expiry: String) {
        val card = Card(
            UUID.randomUUID().toString(),
            "2,700,20",
            cardNo,
            expiry
        )
        var list = _cardList.value!!.plus(card).toMutableList()
        _cardList.postValue(list)
    }
}