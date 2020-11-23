package com.winphyoethu.networkcheckwithrx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.winphyoethu.networkcheckwithrx.util.NetworkObservable
import com.winphyoethu.networkcheckwithrx.util.NetworkState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private lateinit var networkObservable: Observable<NetworkState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkObservable = NetworkObservable.getInstance()
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                when (it) {
                    NetworkState.Wifi -> {
                        tvNetworkState.text = "Connected with WIFI"
                        ivNetworkState.setImageResource(R.drawable.ic_connected)
                    }
                    NetworkState.Mobile -> {
                        tvNetworkState.text = "Connected with Mobile Data"
                        ivNetworkState.setImageResource(R.drawable.ic_connected)
                    }
                    NetworkState.Disconnected -> {
                        tvNetworkState.text = "Disconnected"
                        ivNetworkState.setImageResource(R.drawable.ic_disconnected)
                    }
                }
            }

    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(networkObservable.subscribe())
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}