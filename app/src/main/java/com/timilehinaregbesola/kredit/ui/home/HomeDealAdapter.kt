package com.timilehinaregbesola.kredit.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timilehinaregbesola.kredit.data.model.Deal
import com.timilehinaregbesola.kredit.databinding.HomeDealItemBinding

class HomeDealAdapter :
    ListAdapter<Deal, HomeDealAdapter.ViewHolder>(COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.fromParent(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deal = getItem(position)
        deal?.let {
            holder.bind(it)
        }
    }


    class ViewHolder private constructor(val homeDealItemBinding: HomeDealItemBinding):
        RecyclerView.ViewHolder(homeDealItemBinding.root) {
        companion object{
            fun fromParent(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeDealItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }

        fun bind(deal: Deal){
            homeDealItemBinding.deal = deal
            homeDealItemBinding.executePendingBindings()
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Deal>() {
            override fun areItemsTheSame(oldItem: Deal, newItem: Deal): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Deal, newItem: Deal): Boolean =
                oldItem == newItem
        }
    }

}