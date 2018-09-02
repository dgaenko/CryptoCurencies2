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
import javax.inject.Inject
import butterknife.BindView
import butterknife.ButterKnife

import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.di.Injectable
import ru.familion.cryptocurencies.vm.CurrencyDetailsViewModel
import ru.familion.cryptocurencies.databinding.CurrencyInfoFragmentBinding
import ru.familion.cryptocurencies.util.Utility


class CurrencyFragment: Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var detailsViewModel: CurrencyDetailsViewModel

    lateinit var binding: CurrencyInfoFragmentBinding

    @Inject
    lateinit var utility: Utility

    @BindView(R.id.imgCurrencyIcon)
    lateinit var imgCurrencyIcon: ImageView


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
        ButterKnife.bind(this, binding.root)

        observersRegisters()

        return binding.root
    }

    fun observersRegisters() {
        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyDetailsViewModel::class.java)

        detailsViewModel.getCurrency().observe(this, Observer {
            binding.currency = it
            binding.utility = utility
            imgCurrencyIcon.setImageDrawable(utility.getItemDrawable(it!!.symbol))
        })
    }

}