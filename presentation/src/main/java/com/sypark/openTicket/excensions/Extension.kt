package com.sypark.openTicket.excensions

import android.content.Context
import com.sypark.openTicket.R
import com.sypark.openTicket.util.TwoBtnDialogBuilder

fun Context.showSearchDeleteTwoButtonPopup(
    title: String,
    cancelButtonName: String,
    cancel: () -> Unit = {},
    confirmButtonName: String,
    confirm: () -> Unit
): TwoBtnDialogBuilder {
    return TwoBtnDialogBuilder(this, R.layout.popup_two_button).apply {
        initView(R.id.tvTitle, R.id.btnTwo, R.id.btnOne)
        setTitle(title)
        setAgreementPositiveButton(confirmButtonName) {
            dismiss()
            confirm()
        }
        setNegativeButton(cancelButtonName) {
            dismiss()
            cancel()
        }
        create()
        show(0.8)
    }
}