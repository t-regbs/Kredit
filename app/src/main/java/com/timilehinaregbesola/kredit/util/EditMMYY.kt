package com.timilehinaregbesola.kredit.util

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText


class EditMMYY : TextInputEditText, TextWatcher {
    private var sPrev = ""
    var mon = 0
        private set
    var year = 0
        private set

    private fun InitValue() {
        inputType = InputType.TYPE_CLASS_NUMBER
        filters = arrayOf<InputFilter>(LengthFilter(5))
        hint = "MM/YY"
    }

    constructor(context: Context?) : super(context!!) {
        InitValue()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        InitValue()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        InitValue()
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val sNew = s.toString()
        val newLen = sNew.length
        if (sNew == sPrev) {
            return
        }
        when (newLen) {
            0 -> {
                mon = 0
                year = 0
                sPrev = sNew
            }
            1 -> {
                mon = sNew.toInt()
                year = 0
                sPrev =
                    if (sPrev.isEmpty() && mon > 1) {
                        String.format("%02d/", mon)
                    } else {
                        sNew
                    }
            }
            2 -> {
                mon = sNew.toInt()
                year = 0
                if (sPrev.length == 1) {
                    if (mon in 1..12) {
                        sPrev = String.format("%02d/", mon)
                    }
                } else {
                    sPrev = sNew
                }
            }
            3 -> {
                mon = sNew.substring(0, 2).toInt()
                year = 0
                if (sPrev.length == 2) {
                    mon = sNew.substring(0, 2).toInt()
                    year = sNew.substring(2, 3).toInt()
                    sPrev = String.format("%02d/%d", mon, year)
                } else {
                    sPrev = sNew
                }
            }
            4, 5 -> {
                mon = sNew.substring(0, 2).toInt()
                year = sNew.substring(3, newLen).toInt()
                sPrev = sNew
            }
            else -> sPrev = sNew
        }
        setText(sPrev)
        setSelection(sPrev.length)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}
}