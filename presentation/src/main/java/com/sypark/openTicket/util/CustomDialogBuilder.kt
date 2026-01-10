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

class CustomDialogBuilder(context: Context, resource: Int) {

    private val mContext = context
    lateinit var tvTitle: TextView
    lateinit var tvContent: TextView
    lateinit var btnOk: Button

    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(view)
    }

    private val view: View by lazy {
        View.inflate(context, resource, null)
    }
    var dialog: AlertDialog? = null

    fun initView(titleId: Int, contentId: Int, btnId: Int) {
        tvTitle = view.findViewById(titleId)
        tvContent = view.findViewById(contentId)
        btnOk = view.findViewById(btnId)
    }

    fun initView(contentId: Int, btnId: Int) {
        tvContent = view.findViewById(contentId)
        btnOk = view.findViewById(btnId)
    }

    fun setTitle(@StringRes titleId: Int): CustomDialogBuilder {
        tvTitle.text = mContext.getText(titleId)
        return this
    }

    fun setTitle(title: CharSequence): CustomDialogBuilder {
        tvTitle.text = title
        return this
    }

    fun setMessage(@StringRes messageId: Int): CustomDialogBuilder {
        tvContent.text = mContext.getText(messageId)
        return this
    }

    fun setMessage(message: CharSequence): CustomDialogBuilder {
        tvContent.text = message
        return this
    }

    fun setPositiveButton(@StringRes textId: Int, listener: (view: View) -> (Unit)): CustomDialogBuilder {
        btnOk.apply {
            text = mContext.getText(textId)
            setOnClickListener(listener)
        }
        return this
    }

    fun setPositiveButton(text: CharSequence, listener: (view: View) -> (Unit)): CustomDialogBuilder {
        btnOk.apply {
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