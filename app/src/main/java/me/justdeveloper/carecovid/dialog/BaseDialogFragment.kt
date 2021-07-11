package me.justdeveloper.carecovid.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import me.justdeveloper.carecovid.activity.BaseActivity
import me.justdeveloper.carecovid.R
import me.justdeveloper.carecovid.network.responses.DataWrapper

open class BaseDialogFragment : DialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.let {
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.attributes?.windowAnimations = R.style.DialogAnimation
            }
        }
    }

    protected fun <M> showErrors(wrapper: DataWrapper<M>) {
        try {
            val errorHandler = (activity as BaseActivity)
            errorHandler.showErrors(wrapper)
        } catch (e: ClassCastException) {
            view?.let {
                Snackbar.make(
                    it,
                    "Parent Activity MUST extend BaseActivity",
                    Snackbar.LENGTH_INDEFINITE
                ).show()
            }
        }
    }

    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    protected fun showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, resId, duration).show()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isAdded) {
            super.showNow(manager, tag)
        }
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        if (!isAdded) {
            super.showNow(manager, tag)
        }
    }
}
