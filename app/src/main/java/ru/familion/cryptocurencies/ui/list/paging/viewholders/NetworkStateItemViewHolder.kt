package ru.familion.cryptocurencies.ui.list.paging.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import ru.familion.cryptocurencies.R
import ru.familion.cryptocurencies.repo.net.NetworkState


class NetworkStateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val progressBar: ProgressBar
    private val errorMsg: TextView

    init {
        progressBar = itemView.findViewById(R.id.progress_bar)
        errorMsg = itemView.findViewById(R.id.error_msg)
    }


    fun bindView(networkState: NetworkState?) {
        if (networkState != null && networkState!!.status === NetworkState.Status.RUNNING) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }

        if (networkState != null && networkState!!.status === NetworkState.Status.FAILED) {
            errorMsg.visibility = View.VISIBLE
            errorMsg.setText(networkState!!.msg)
        } else {
            errorMsg.visibility = View.GONE
        }
    }
}