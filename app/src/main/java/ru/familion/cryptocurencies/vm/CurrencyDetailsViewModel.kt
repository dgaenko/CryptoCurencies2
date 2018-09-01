package ru.familion.cryptocurencies.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

import ru.familion.cryptocurencies.model.Data


class CurrencyDetailsViewModel @Inject constructor(): ViewModel() {

    init {
        setVisible(false)
    }

    fun getCurrency(): MutableLiveData<Data> = data
    fun setCurrency(newData: Data) { data.value = newData }

    fun getVisible(): MutableLiveData<Boolean> = visible
    fun setVisible(newValue: Boolean) { visible.value = newValue }

    companion object {
        private var data: MutableLiveData<Data> = MutableLiveData<Data>()
        private var visible: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    }

}