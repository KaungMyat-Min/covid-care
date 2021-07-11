package me.justdeveloper.carecovid.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.default_progress_bar.*
import kotlinx.android.synthetic.main.default_tool_bar.*
import me.justdeveloper.carecovid.R
import me.justdeveloper.carecovid.dialog.ConfirmationDialog
import me.justdeveloper.carecovid.network.responses.DataWrapper
import me.justdeveloper.carecovid.network.responses.Status

abstract class BaseActivity : AppCompatActivity() {

    private val alertDialog by lazy {
        ConfirmationDialog("Oops", "error", true, null)
    }

    abstract fun initDependencies(savedInstanceState: Bundle?)
    abstract fun getActionBarTitle(): String?
    protected open fun getDefaultProgressBar(): View? = default_progress_bar

    protected open fun getLayoutToHideWhenLoading(): View? = null
    protected open fun setupActionBar() {
        defaultToolBar?.let {
            setSupportActionBar(it)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            tvAppBarTitle.text = getActionBarTitle()
            it.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isTablet = resources.getBoolean(R.bool.isTablet)
        requestedOrientation = if (isTablet) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        initDependencies(savedInstanceState)
    }


    override fun setTitle(title: CharSequence?) {
        if (tvAppBarTitle != null) {
            tvAppBarTitle.text = title
        } else {
            super.setTitle(title)
        }
    }


    // -------- helper functions ---------
    protected fun <M> toggleProgress(wrapper: DataWrapper<M>) {
        val progressBar = getDefaultProgressBar()

        if (wrapper.isLoading) {
            if (progressBar?.visibility != View.VISIBLE) {
                progressBar?.visibility = View.VISIBLE
                getLayoutToHideWhenLoading()?.visibility = View.INVISIBLE
                progressBar?.let {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                }
            }
        } else {
            if (progressBar?.visibility == View.VISIBLE) {
                getLayoutToHideWhenLoading()?.visibility = View.VISIBLE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                progressBar.visibility = View.GONE
            }
        }
    }

    fun <M> showErrors(wrapper: DataWrapper<M>) {
        wrapper.apiErrors?.let {
            if (it.isNotEmpty()) {
                it.first().errorMessage?.let {
                    showDialog(it)
                }
            }
        }
        wrapper.status?.let {
            when (it) {

                Status.NO_CONNECTION -> showDialog(getString(R.string.error_no_internet_connection))

                Status.SERVER_DOWN -> showDialog(getString(R.string.error_server_down))

                Status.ERROR_UNKNOWN -> wrapper.error?.message?.let { showDialog(it) }

                Status.ERROR -> {
                    val message = wrapper.responseMessage
                        ?: wrapper.apiErrors?.firstOrNull()?.errorMessage
                        ?: wrapper.errorMessage
                        ?: "Unknown Error"
                    showDialog(message)
                }

                else -> {
                }
            }
        }
    }


    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(baseContext, message, duration).show()
    }

    protected fun showDialog(message: String) {
        if (!alertDialog.isShow() && !alertDialog.isAdded) {
            alertDialog.newMessage = message
            alertDialog.showNow(supportFragmentManager, "error_dialog")
        }
    }
}
