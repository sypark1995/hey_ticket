package com.sypark.openTicket.excensions

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
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

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Int.dpToPx(): Int {
    val density = Resources.getSystem().displayMetrics.density
    return (this * density).toInt()
}

fun View.setMargins(
    left: Int = this.marginLeft,
    top: Int = this.marginTop,
    right: Int = this.marginRight,
    bottom: Int = this.marginBottom,
) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }
}
