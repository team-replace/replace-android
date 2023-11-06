package com.app.replace.ui.common.listener

import android.os.SystemClock
import android.view.View

class OnSingleClickListener(
    private var interval: Int = INTERVAL_TIME,
    private var onSingleClick: (View) -> Unit,
) : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if ((elapsedRealtime - lastClickTime) < interval) {
            return
        }
        lastClickTime = elapsedRealtime
        onSingleClick(v)
    }

    companion object {
        private const val INTERVAL_TIME = 600
    }
}
