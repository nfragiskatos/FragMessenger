package com.nfragiskatos.fragmessenger.utility

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(activity: Activity) {
    val view = activity.currentFocus ?: View(activity)
    (activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
        hideSoftInputFromWindow(view.windowToken, 0)
    }
}