package com.winphyoethu.networkcheckwithrx

import android.app.Application
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.winphyoethu.networkcheckwithrx.util.NetworkChangeReceiver

class MainApplication : Application(), LifecycleObserver {

    private val networkChangeReceiver: NetworkChangeReceiver by lazy {
        NetworkChangeReceiver()
    }

    private val intentFilter: IntentFilter by lazy {
        IntentFilter().apply {
            this.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            this.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        }
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        unregisterReceiver(networkChangeReceiver)
    }

}