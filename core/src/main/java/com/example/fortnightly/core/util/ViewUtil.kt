package com.example.fortnightly.core.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View?.hideKeyboard() {
    (this?.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager )
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}