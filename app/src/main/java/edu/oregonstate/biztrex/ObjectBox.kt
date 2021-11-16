package edu.oregonstate.biztrex

import android.content.Context
import io.objectbox.BoxStore

/**
 * Implementation for local object persistence
 * https://docs.objectbox.io/getting-started
 */
object ObjectBox {

    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}