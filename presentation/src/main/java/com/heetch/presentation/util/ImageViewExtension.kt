package com.heetch.presentation.util

import android.widget.ImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun ImageView.loadImagefromUrl(url: String): Disposable =
    RxPicasso().loadImage(url)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe { setImageBitmap(it) }