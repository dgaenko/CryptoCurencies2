package ru.familion.cryptocurencies.repo.net.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import android.util.Log
import com.github.ajalt.timberkt.Timber.d
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.familion.cryptocurencies.api.CoinmarketcapApi
import ru.familion.cryptocurencies.model.ApiResponse

import ru.familion.cryptocurencies.model.Data
import ru.familion.cryptocurencies.repo.net.NetworkState
import rx.subjects.ReplaySubject


class NetPositionalDataSource(private val api: CoinmarketcapApi) : PositionalDataSource<Data>() {

    val currenciesObservable: ReplaySubject<Data> = ReplaySubject.create<Data>();
    var retry: ( () -> Any )? = null


    fun geCurrencies() = currenciesObservable

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Data>) {
        d { "loadInitial, requestedStartPosition = " + params.requestedStartPosition + ", requestedLoadSize = " + params.requestedLoadSize }

        retry = { loadInitial(params, callback) }
        callApi(params.requestedStartPosition, params.requestedLoadSize) {
            repos, next -> callback.onResult(repos, next)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Data>) {
        d { "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize }

        retry = { loadRange(params, callback) }
        callApi(params.startPosition, params.loadSize) { repos, next ->
            callback.onResult(repos)
        }
    }

    private fun callApi(startPosition: Int, limit: Int, callback: (repos: List<Data>, next: Int) -> Unit) {
        networkOperationState.postValue(NetworkState.LOADING)

        val call = api.getPage(startPosition + 1, limit)
        call.enqueue(object : Callback<ApiResponse> {

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                responseFailed(-1, t.message!!)
                callback(ArrayList<Data>(0), 0)
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.code() == 200 && response.body()?.data != null) {
                    networkOperationState.postValue(NetworkState.LOADED)
                    val rb = response.body()
                    callback(rb!!.data, 0)
                    rb!!.data.forEachIndexed { index, item ->
                        item.num = startPosition + index
                        currenciesObservable.onNext(item)
                    }
                } else {
                    responseFailed(response.code(), response.message())
                }
            }

        })
    }

    private fun responseFailed(httpCode: Int, message: String) {
        d { "Response error! message:" + message + " httpCode:" + httpCode }
        val state = NetworkState(NetworkState.Status.FAILED, message)
        networkOperationState.postValue(state)
    }

    companion object {
        val networkOperationState = MutableLiveData<NetworkState>()
        fun getNetworOperationkState() = networkOperationState
    }

}

