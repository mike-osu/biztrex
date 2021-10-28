package edu.oregonstate.biztrex

import android.app.Application

class BizTrexApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}