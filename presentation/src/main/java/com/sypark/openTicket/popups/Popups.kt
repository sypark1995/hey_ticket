package com.sypark.openTicket.popups

import android.content.Context
import com.sypark.openTicket.R
import com.sypark.openTicket.excensions.showSearchDeleteTwoButtonPopup

fun Context.showSearchDeletePopup(cancel: () -> Unit = {}, confirm: () -> Unit = {}) {
    showSearchDeleteTwoButtonPopup(
        title = getString(R.string.delete_recently_search),
        cancelButtonName = getString(R.string.cancel),
        cancel = cancel,
        confirmButtonName = getString(R.string.delete),
        confirm = confirm
    )
}

fun Context.showRegisterClosePopup(cancel: () -> Unit = {}, confirm: () -> Unit = {}) {
    showSearchDeleteTwoButtonPopup(
        title = getString(R.string.register_close_popup),
        cancelButtonName = getString(R.string.cancel),
        cancel = cancel,
        confirmButtonName = getString(R.string.register_close_confirm),
        confirm = confirm
    )
}