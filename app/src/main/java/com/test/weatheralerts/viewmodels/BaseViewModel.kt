package com.test.weatheralerts.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    fun Disposable.addSubscription() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}