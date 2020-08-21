package com.example.fortnightly.core.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

abstract class Event<T> {

    protected open var hasBeenHandled = AtomicBoolean(false)
    protected open var content: T? = null

    open fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled.compareAndSet(false, true)) {
            content
        } else {
            null
        }
    }

    open fun peekContent(): T? = content

    class Data<T>(override var content: T?) : Event<T>()

    class Callback : Event<Any>() {
        override var content: Any? = "CALLBACK"
    }
}

@JvmName("observeDataEvent")
fun <T> LiveData<Event.Data<T>>.observeEvent(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer { event -> event.getContentIfNotHandled()?.let { observer(it) } })
}

@JvmName("observeCallbackEvent")
fun LiveData<Event.Callback>.observeEvent(owner: LifecycleOwner, observer: () -> Unit) {
    observe(owner, Observer { event -> event.getContentIfNotHandled()?.let { observer() } })
}

fun MutableLiveData<Event.Callback>.call() {
    this.value = Event.Callback()
}