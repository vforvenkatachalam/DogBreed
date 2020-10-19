package com.dog.breed.views.base

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import com.dog.breed.R


open class HeaderActivity : MyAppCompatActivity() {

    private var backIV: ImageView? = null
    private var titleTV: TextView? = null


    override fun initObservers() {

    }

    override fun onErrorCalled(it: String?) {
        showSnackbar(it)
    }


    protected fun updateHeader(
        headerLayout: View,
        isShowBack: Boolean,
        titleText: String,
        listener: OnHeaderClickListener?
    ) {

        backIV = headerLayout.findViewById(R.id.backIV)
        titleTV = headerLayout.findViewById(R.id.titleTV)

        if (titleText != "" && titleText.isNotEmpty()) {
            titleTV!!.text = titleText
        } else {
            titleTV!!.text = ""
        }

        backIV!!.setOnClickListener {
            listener?.onBackClicked()
        }

    }

    private fun textCleared(){
        titleTV!!.visibility = VISIBLE
    }

    interface OnHeaderClickListener {
        fun onBackClicked()
    }
}