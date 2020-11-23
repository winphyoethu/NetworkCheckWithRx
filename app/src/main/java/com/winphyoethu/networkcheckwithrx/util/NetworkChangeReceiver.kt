package com.winphyoethu.networkcheckwithrx.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkChangeReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        context?.let {
            NetworkUtil.getInstance().getConnectionStatus(it)
        }

    }

}