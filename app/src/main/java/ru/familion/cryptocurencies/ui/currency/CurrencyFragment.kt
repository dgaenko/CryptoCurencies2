package ru.familion.cryptocurencies.ui.currency

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Date
import butterknife.BindView
import butterknife.ButterKnife
import com.github.ajalt.timberkt.Timber.d

import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.di.Injectable
import ru.familion.cryptocurencies.vm.CurrencyDetailsViewModel
import ru.familion.cryptocurencies.databinding.CurrencyInfoFragmentBinding
import ru.familion.cryptocurencies.util.Utility


class CurrencyFragment: Fragment(), Injectable {

    private lateinit var detailsViewModel: CurrencyDetailsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var utility: Utility
    lateinit var binding: CurrencyInfoFragmentBinding

    @BindView(R.id.imgCurrencyIcon)
    lateinit var imgCurrencyIcon: ImageView
    @BindView(R.id.txtCurrencyInfo1h)
    lateinit var txtCurrencyInfo1h: TextView
    @BindView(R.id.txtCurrencyInfo24h)
    lateinit var txtCurrencyInfo24h: TextView
    @BindView(R.id.txtCurrencyInfo7d)
    lateinit var txtCurrencyInfo7d: TextView

    @BindView(R.id.txtCurrencyInfoLastUpdated)
    lateinit var txtCurrencyInfoLastUpdated: TextView


    override fun onResume() {
        super.onResume()
        detailsViewModel.setVisible(true)
    }

    override fun onPause() {
        super.onPause()
        detailsViewModel.setVisible(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.currency_info_fragment, container, false)

        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyDetailsViewModel::class.java)

        ButterKnife.bind(this, binding.root)

        detailsViewModel.getCurrency().observe(this, Observer {
            binding.currency = it

            imgCurrencyIcon.setImageDrawable(utility.getItemDrawable(it!!.symbol))
            txtCurrencyInfo1h.setTextColor(utility.getValueColor(it!!.quotes.uSD.percent_change_1h))
            txtCurrencyInfo24h.setTextColor(utility.getValueColor(it!!.quotes.uSD.percent_change_24h))
            txtCurrencyInfo7d.setTextColor(utility.getValueColor(it!!.quotes.uSD.percent_change_7d))

            val dateObject = Date(it.last_updated * 1000L)
            txtCurrencyInfoLastUpdated.setText(formatDate(dateObject))
        })

        return binding.root
    }

    private fun formatDate(dateObject: Date): String {
        val dateFormat = SimpleDateFormat(getString(R.string.format_currency_last_updated))
        return dateFormat.format(dateObject)
    }

}