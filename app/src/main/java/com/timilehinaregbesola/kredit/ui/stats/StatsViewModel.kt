package com.timilehinaregbesola.kredit.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timilehinaregbesola.kredit.data.model.Card
import com.timilehinaregbesola.kredit.data.model.Loan
import java.util.*

class StatsViewModel : ViewModel() {

    private val _loanList = MutableLiveData<MutableList<Loan>>()
    val loanList: LiveData<MutableList<Loan>> = _loanList

    fun createFakeLoans(): List<Loan> {
        val loan1 = Loan(UUID.randomUUID().toString(), "Research Loan", "6 months", "This loan is for those who wish to carry out research")
        val loan2 = Loan(UUID.randomUUID().toString(), "Business Loan", "1 month", "This loan is for fashion inclined businesses" )
        val loan3 = Loan(UUID.randomUUID().toString(), "Business Loan", "2 years", "This is for food business")
        return listOf(loan1, loan2, loan3)
    }

    fun addNewLoan(type: String, date: String, info: String = "This loan is for fashion inclined businesses", interest: String) {
        val loan = Loan(
            UUID.randomUUID().toString(),
            type,
            date,
            interest,
            info
        )
        var list = mutableListOf<Loan>()
        list = if (_loanList.value == null) {
            mutableListOf(loan)
        } else {
            _loanList.value!!.plus(loan).toMutableList()
        }
        _loanList.postValue(list)
    }
}