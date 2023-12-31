package com.app.replace.ui.common

import android.view.View
import com.app.replace.R
import com.app.replace.ui.common.listener.OnSingleClickListener
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.replace.data.common.FetchState

fun View.makeSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(context.getColor(R.color.white_FFFFFF))
        .setTextColor(context.getColor(R.color.gray_434343))
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
}

fun View.showNetworkErrorMessage(fetchState: FetchState) {
    when (fetchState) {
        is FetchState.BadInternet -> makeSnackbar(this.context.getString(R.string.bad_internet_message))
        is FetchState.WrongConnection -> makeSnackbar(this.context.getString(R.string.wrong_connection_message))
        is FetchState.ParseError -> makeSnackbar(this.context.getString(R.string.parse_error_message))
        is FetchState.Fail -> makeSnackbar(this.context.getString(R.string.fail_message))
    }
}

fun View.showUnexpectedErrorMessage() {
    makeSnackbar(this.context.getString(R.string.unexpected_error_message))
}

fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    val oneClick = OnSingleClickListener {
        onSingleClick(it)
    }
    setOnClickListener(oneClick)
}
