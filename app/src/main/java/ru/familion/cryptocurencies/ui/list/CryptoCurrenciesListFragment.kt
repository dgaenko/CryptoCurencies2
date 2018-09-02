package ru.familion.cryptocurencies.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import com.github.ajalt.timberkt.Timber.d
import javax.inject.Inject

import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.di.Injectable
import ru.familion.cryptocurencies.ui.list.paging.CurrenciesListPagedAdapter
import ru.familion.cryptocurencies.vm.CurrenciesListViewModel
import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.vm.CurrencyDetailsViewModel


class CryptoCurrenciesListFragment: Fragment(), ItemClickListener, Injectable {

    //@BindView(R.id.currenciesReciclerView)
    //lateinit var recicleView: RecyclerView

    lateinit var adapter: CurrenciesListPagedAdapter

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var listViewModel: CurrenciesListViewModel
    lateinit var detailsViewModel: CurrencyDetailsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.currencies_list_fragment, container, false)

        //ButterKnife.bind(this, view)
        val recicleView = view.findViewById<RecyclerView>(R.id.currenciesReciclerView)

        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrenciesListViewModel::class.java)
        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyDetailsViewModel::class.java)

        observersRegisters()

        adapter = CurrenciesListPagedAdapter(this)
        recicleView.setAdapter(adapter)

        return view
    }

    override fun OnItemClick(data: Data) {
        d { "OnItemClick data:" + data }
        detailsViewModel.setCurrency(data)
    }

    fun observersRegisters() {
        listViewModel.getCurrenciesList().observe(this, Observer {
            pagedList -> adapter.submitList(pagedList)
        })
        listViewModel.getNetworkState().observe(this, Observer {
            networkState -> adapter.setNetworkState(networkState!!)
        })
    }

}