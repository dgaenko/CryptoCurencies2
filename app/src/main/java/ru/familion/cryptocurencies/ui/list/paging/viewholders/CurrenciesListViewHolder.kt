package ru.familion.cryptocurencies.ui.list.paging.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.ui.list.ItemClickListener
import ru.familion.cryptocurencies.databinding.CurrencyItemBinding
import ru.familion.cryptocurencies.util.Utility


class CurrenciesListViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

    private lateinit var binding: CurrencyItemBinding
    private lateinit var view: View
    private lateinit var utility: Utility
    private var currency: Data? = null

    private val imgCurrencyIcon: ImageView = view.findViewById(R.id.imgCurrencyIcon)
    private val txt24hValue: TextView = view.findViewById(R.id.txt24hValue)
    private val txt7dValue: TextView = view.findViewById(R.id.txt7dValue)


    constructor(binding: CurrencyItemBinding): this(binding.root) {
        this.binding = binding
        view = binding.root
        utility = Utility(binding.root.context)
    }

    init {
        view.setOnClickListener(this)
    }

    fun bind(data: Data?) {
        currency = data
        binding.currency = data
        binding.executePendingBindings();

        data.let {
            imgCurrencyIcon.setImageDrawable(utility.getItemDrawable(it!!.symbol))
            txt24hValue.setTextColor(utility.getValueColor(it!!.quotes.uSD.percent_change_24h))
            txt7dValue.setTextColor(utility.getValueColor(it!!.quotes.uSD.percent_change_7d))
        }
    }

    override fun onClick(view: View) {
        itemClickListener.let {
            it.OnItemClick(currency!!)
        }
    }

    companion object {
        lateinit var itemClickListener: ItemClickListener

        fun create(binding: CurrencyItemBinding, itemClickListener: ItemClickListener): CurrenciesListViewHolder {
            this.itemClickListener = itemClickListener
            return CurrenciesListViewHolder(binding)
        }
    }
}