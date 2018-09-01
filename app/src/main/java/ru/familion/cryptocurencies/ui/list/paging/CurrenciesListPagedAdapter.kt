package ru.familion.cryptocurencies.ui.list.paging

import android.arch.paging.PagedListAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.databinding.CurrencyItemBinding
import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.repo.net.NetworkState
import ru.familion.cryptocurencies.ui.list.ItemClickListener
import ru.familion.cryptocurencies.ui.list.paging.viewholders.CurrenciesListViewHolder
import ru.familion.cryptocurencies.ui.list.paging.viewholders.NetworkStateItemViewHolder

class CurrenciesListPagedAdapter(val itemClickListener: ItemClickListener): PagedListAdapter<Data, RecyclerView.ViewHolder>(diffUtilCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        var viewHolder: RecyclerView.ViewHolder

        if (viewType == R.layout.currency_item) {
            val binding = DataBindingUtil.inflate<CurrencyItemBinding>(layoutInflater, R.layout.currency_item, parent, false)
            viewHolder = CurrenciesListViewHolder.create(binding, itemClickListener)
        } else if (viewType == R.layout.network_state_item) {
            val view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            viewHolder = NetworkStateItemViewHolder(view)
        } else {
            throw IllegalArgumentException("unknown view type")
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.currency_item -> (holder as CurrenciesListViewHolder).bind(getItem(position))
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindView(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.currency_item
        }
    }

    private fun hasExtraRow(): Boolean {
        return if (networkState != null && networkState !== NetworkState.LOADED) {
            true
        } else {
            false
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState

        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }

    }

    companion object {
        private val diffUtilCallback = object: DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem
        }
    }

}