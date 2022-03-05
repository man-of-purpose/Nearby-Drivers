package com.heetch.presentation.util

import android.graphics.Bitmap
import io.reactivex.Observable

interface ImageDownloader {

    fun loadImage(url:String) : Observable<Bitmap>
}