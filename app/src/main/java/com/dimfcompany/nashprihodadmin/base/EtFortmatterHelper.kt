package com.dimfcompany.nashprihodadmin.base

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.dimfcompany.nashprihodadmin.base.extensions.removeRepeatingSpaces
import com.dimfcompany.nashprihodadmin.base.extensions.replaceRepeatingsWithSingle

class EtFortmatterHelper
{
    companion object
    {
        fun addEtCommasFormatter(et: EditText)
        {
            var text_watcher: TextWatcher? = null
            text_watcher = object : TextWatcher
            {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
                {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)
                {
                    et.removeTextChangedListener(text_watcher!!)
                    var text = et.text.toString()
                    text = text.removeRepeatingSpaces()
//                            .replace(" ", ", ")
                            .replaceRepeatingsWithSingle(',')
                    et.setText(text)
                    et.setSelection(text.length)
                    et.addTextChangedListener(text_watcher)
                }

                override fun afterTextChanged(p0: Editable?)
                {

                }
            }

            et.addTextChangedListener(text_watcher)
        }
    }
}