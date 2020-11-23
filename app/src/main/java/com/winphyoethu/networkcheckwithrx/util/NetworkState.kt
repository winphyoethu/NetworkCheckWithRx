package com.winphyoethu.networkcheckwithrx.util

sealed class NetworkState {

    object Wifi : NetworkState()

    object Mobile : NetworkState()

    object Disconnected : NetworkState()

}