package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun Activity.isKeyboardClosed(): Boolean {
    val rootView = window.decorView.rootView
    val result: Boolean

    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)
    val screenHeight = rootView.height

    val keypadHeight = screenHeight - r.bottom
    result = keypadHeight > screenHeight * 0.15

    return !result
}

fun Activity.isKeyboardOpen() = !isKeyboardClosed()