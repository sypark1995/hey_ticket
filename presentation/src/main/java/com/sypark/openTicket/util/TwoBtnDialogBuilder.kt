package com.sypark.openTicket.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes


/**
 * 제목, 내용이 있는 CustomDialogBuilder
 * 버튼 2개
 */

class TwoBtnDialogBuilder(context: Context, resource: Int) {
    private val mContext = context
    lateinit var tvTitle: TextView
    lateinit var btnOk: Button
    lateinit var btnCancel: Button

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(view).setCancelable(false)
    }

    private val view: View by lazy {
        View.inflate(context, resource, null)
    }
    var dialog: AlertDialog? = null

    fun initView(titleId: Int, leftBtnId: Int, rightBtnId: Int) {
        tvTitle = view.findViewById(titleId)
        btnOk = view.findViewById(leftBtnId)
        btnCancel = view.findViewById(rightBtnId)
    }

    fun setTitle(@StringRes titleId: Int): TwoBtnDialogBuilder {
        tvTitle.text = mContext.getText(titleId)
        return this
    }

    fun setTitle(title: CharSequence): TwoBtnDialogBuilder {
        tvTitle.text = title
        return this
    }

    fun setPositiveButton(
        @StringRes textId: Int,
        listener: (view: View) -> (Unit)
    ): TwoBtnDialogBuilder {
        btnOk.apply {
            text = mContext.getText(textId)
            setOnClickListener(listener)
        }
        return this
    }

    fun setPositiveButton(
        text: CharSequence,
        listener: (view: View) -> (Unit)
    ): TwoBtnDialogBuilder {
        btnOk.apply {
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }

    fun setAgreementPositiveButton(
        text: CharSequence,
        listener: (view: View) -> (Unit)
    ): TwoBtnDialogBuilder {
        btnOk.apply {
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }

    fun setNegativeButton(
        @StringRes textId: Int,
        listener: (view: View) -> (Unit)
    ): TwoBtnDialogBuilder {
        btnCancel.apply {
            text = mContext.getText(textId)
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }

    fun setNegativeButton(
        text: CharSequence,
        listener: (view: View) -> (Unit)
    ): TwoBtnDialogBuilder {
        btnCancel.apply {
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }

    fun create() {
        dialog = builder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show(ratio: Double) {
        dialog?.show()

        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        size.x // 디바이스 가로 길이

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * ratio).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}