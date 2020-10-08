package com.timilehinaregbesola.kredit.ui.stats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timilehinaregbesola.kredit.data.model.Loan
import com.timilehinaregbesola.kredit.databinding.LoanItemBinding


class LoanAdapter(val clickListener: LoanListener) : ListAdapter<Loan, LoanAdapter.ViewHolder>(COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loan = getItem(position)
        loan?.let {
            holder.bind(it, clickListener)
        }
    }


    class ViewHolder private constructor(val loanItemBinding: LoanItemBinding):
        RecyclerView.ViewHolder(loanItemBinding.root) {
        companion object{
            fun fromParent(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LoanItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

        fun bind(loan: Loan, clickListener: LoanListener){
            loanItemBinding.loan = loan
            loanItemBinding.clickListener = clickListener
            loanItemBinding.executePendingBindings()
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Loan>() {
            override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean =
                oldItem == newItem
        }
    }

    class LoanListener(val clickListener: (loan: Loan) -> Unit) {
        fun onclick(loan: Loan) = clickListener(loan)
    }

}