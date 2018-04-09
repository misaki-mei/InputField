package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import com.snowyuki.inputfieldlibrary.storage.StoreHelper
import kotlinx.android.synthetic.main.field_number.view.*

class NumberField : InputField{

    var value : Float = 0f
        set(value) {
            field = value
            if_valueEditText.setText(value.toString())
        }
        get(){
            try {
                return if_valueEditText.text.toString().toFloat()
            }catch (e : Exception){ }
            return 0f
        }

    var error : String
        set(value) {if_valueEditText.error = error}
        get() = ""

    var hint : String
        set(value){if_valueEditText.hint = hint}
        get() = ""

    var maxValue = Float.MAX_VALUE
    var minValue = Float.MIN_VALUE

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}


    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_number,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.NumberField)
            if_nameTextView.text = title
            value = ta.getFloat(R.styleable.NumberField_if_number,0f)
            value = StoreHelper.getInstance(context).getFloat(key,value)
            if_valueEditText.hint = ta.getString(R.styleable.NumberField_if_hint)?:""
            maxValue = ta.getFloat(R.styleable.NumberField_if_maxNumber,Float.MAX_VALUE)
            minValue = ta.getFloat(R.styleable.NumberField_if_minNumber,Float.MIN_VALUE)

            if_valueEditText.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(editable: Editable?) {
                    if(editable == null)return
                    try {
                        val number = editable.toString().toFloat()
                        if(number > maxValue || number < minValue){
                            if_valueEditText.error = context.getString(R.string.input_field_value_out_range)
                        }
                    }catch (e : Exception){
                        if_valueEditText.error = context.getString(R.string.input_field_error_syntax)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)  = Unit
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            })
            ta.recycle()
        }
    }

    override fun save() {
        StoreHelper.getInstance(context).put(key,value)
    }
}