package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.snowyuki.inputfieldlibrary.storage.StoreHelper
import kotlinx.android.synthetic.main.field_text.view.*

class TextField : InputField{

    var value : String = ""
    set(value) {
        field = value
        if_valueEditText.setText(value)
    }
    get() = if_valueEditText.text.toString()

    var error : String
    set(value) {if_valueEditText.error = error}
    get() = ""

    var hint : String
    set(value){if_valueEditText.hint = hint}
    get() = ""

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}


    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_text,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.TextField)
            if_nameTextView.text = title
            value = ta.getString(R.styleable.TextField_if_text)?:""
            value = StoreHelper.getInstance(context).getString(key,value)
            if_valueEditText.hint = ta.getString(R.styleable.TextField_if_hint)?:""
            ta.recycle()
        }
    }

    override fun save() {
        StoreHelper.getInstance(context).put(key,value)
    }
}