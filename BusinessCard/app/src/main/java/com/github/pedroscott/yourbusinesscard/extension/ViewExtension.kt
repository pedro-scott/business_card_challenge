package com.github.pedroscott.yourbusinesscard.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(view: View) =
    try {
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: RuntimeException) {
        e.printStackTrace()
    }