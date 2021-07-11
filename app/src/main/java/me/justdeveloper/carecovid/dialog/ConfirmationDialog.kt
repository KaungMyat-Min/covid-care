package me.justdeveloper.carecovid.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_confirm.*
import me.justdeveloper.carecovid.R

open class ConfirmationDialog(
    private val title: String?,
    private val message: String,
    private val cancelable: Boolean = true,
    private val onPositiveClicked: (() -> Unit)?
) : BaseDialogFragment() {
    var newMessage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_ok.setOnClickListener {
            onPositiveClicked?.invoke()
            this.dismiss()
        }

        if (cancelable) {
            btn_cancel.visibility = View.VISIBLE
            btn_cancel.setOnClickListener {
                dismiss()
            }
        } else {
            btn_cancel.visibility = View.GONE
        }

        title?.let {
            tvTitle.text = title
            gpTitle.visibility = View.VISIBLE
        }

        tvMessage.text = newMessage ?: message
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(cancelable)
            setCanceledOnTouchOutside(cancelable)
        }
    }

    fun isShow() = dialog?.isShowing ?: false
}
