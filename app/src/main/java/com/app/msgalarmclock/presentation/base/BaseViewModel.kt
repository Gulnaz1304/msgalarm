package com.app.msgalarmclock.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val destroyDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!destroyDisposable.isDisposed) {
            destroyDisposable.dispose()
        }
    }

    protected fun Disposable.disposeWhenDestroy() {
        destroyDisposable.add(this)
    }
}