package com.winphyoethu.networkcheckwithrx.util

import io.reactivex.subjects.PublishSubject

class NetworkObservable {

    companion object {
        private var networkSubject: PublishSubject<NetworkState>? = null

        fun getInstance(): PublishSubject<NetworkState> {

            if (networkSubject == null) {
                networkSubject = PublishSubject.create()
            }

            return networkSubject!!
        }
    }

}