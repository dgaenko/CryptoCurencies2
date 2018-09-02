package ru.familion.cryptocurencies.ui

import javax.inject.Inject
import android.os.Bundle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.View
import android.util.Log
import android.util.TypedValue
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

import butterknife.BindView
import butterknife.ButterKnife
import com.github.ajalt.timberkt.Timber.d

import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.vm.CurrenciesListViewModel
import ru.familion.cryptocurencies.vm.CurrencyDetailsViewModel


class MainActivity @Inject constructor(): AppCompatActivity(), HasSupportFragmentInjector, View.OnClickListener {

    lateinit var navController: NavController
    lateinit var detailsViewModel: CurrencyDetailsViewModel

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        toolbar.setNavigationOnClickListener(this)

        navController = findNavController(this, R.id.nav_host_fragment)

        detailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyDetailsViewModel::class.java)

        observersRegisters()
    }

    /**
     * Toolbar navigation button click handler
     */
    override fun onClick(view: View) {
        onSupportNavigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    fun observersRegisters() {
        detailsViewModel.getCurrency().observe(this, Observer {
            navController.navigate(R.id.currencyFragment)
        })
        detailsViewModel.getVisible().observe(this, Observer {
            showBackButton(detailsViewModel.getVisible().value!!)
        })
    }

    fun showBackButton(flag: Boolean) {
        val icon_id: Int =
            if (flag)
                R.drawable.arrow_left
            else
                R.drawable.btc
        toolbar.navigationIcon = resources.getDrawable(icon_id)
    }

}
