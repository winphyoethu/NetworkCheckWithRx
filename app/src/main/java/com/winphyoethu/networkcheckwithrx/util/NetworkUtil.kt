package com.winphyoethu.networkcheckwithrx.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtil {

    companion object {
        private var networkUtil: NetworkUtil? = null

        fun getInstance(): NetworkUtil {
            if (networkUtil == null) {
                networkUtil = NetworkUtil()
            }
            return networkUtil!!
        }

    }

    fun getConnectionStatus(context: Context) {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= 24) {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            if (networkCapabilities != null) {
                when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkObservable.getInstance()
                        .onNext(NetworkState.Wifi)
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkObservable.getInstance()
                        .onNext(NetworkState.Mobile)
                    else -> NetworkObservable.getInstance().onNext(NetworkState.Disconnected)
                }
            } else {
                NetworkObservable.getInstance().onNext(NetworkState.Disconnected)
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null) {
                when (networkInfo.type) {
                    ConnectivityManager.TYPE_WIFI -> NetworkObservable.getInstance().onNext(NetworkState.Wifi)
                    ConnectivityManager.TYPE_MOBILE -> NetworkObservable.getInstance().onNext(NetworkState.Mobile)
                    else -> NetworkObservable.getInstance().onNext(NetworkState.Disconnected)
                }
            } else {
                NetworkObservable.getInstance().onNext(NetworkState.Disconnected)
            }
        }

    }

}