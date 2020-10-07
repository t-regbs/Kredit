package com.timilehinaregbesola.kredit.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timilehinaregbesola.kredit.data.model.Deal
import com.timilehinaregbesola.kredit.data.model.Transaction
import com.timilehinaregbesola.kredit.databinding.HomeDealItemBinding
import com.timilehinaregbesola.kredit.databinding.HomeTransactionItemBinding

class HomeTransactionAdapter :
    ListAdapter<Transaction, HomeTransactionAdapter.ViewHolder>(COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)
        transaction?.let {
            holder.bind(it)
        }
    }


    class ViewHolder private constructor(val homeTransactionItemBinding: HomeTransactionItemBinding):
        RecyclerView.ViewHolder(homeTransactionItemBinding.root) {
        companion object{
            fun fromParent(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeTransactionItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

        fun bind(transaction: Transaction){
            homeTransactionItemBinding.transaction = transaction
            homeTransactionItemBinding.executePendingBindings()
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
                oldItem == newItem
        }
    }

}