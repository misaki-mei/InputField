package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.snowyuki.inputfieldlibrary.storage.StoreHelper
import kotlinx.android.synthetic.main.field_checkbox.view.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange

class CheckBoxField : InputField {

    var checked = false
        set(value) {
            field = value
            changeState(value)
        }

    private var onText : String? = null
    private var offText : String? = null

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}

    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_checkbox,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.CheckBoxField)
            if_nameTextView.text = title
            onText = ta.getString(R.styleable.CheckBoxField_if_onText)
            offText = ta.getString(R.styleable.CheckBoxField_if_offText)
            checked = ta.getBoolean(R.styleable.CheckBoxField_if_checked,false)
            checked = StoreHelper.getInstance(context).getBoolean(key,checked)
            ta.recycle()
        }

        if_checkbox.onCheckedChange { _, isChecked ->
            checked = isChecked
        }

        setOnClickListener {
            checked = !checked
        }
    }

    private fun changeState(state: Boolean) {
        if_checkbox.isChecked = state
        if(state && onText!=null){
            if_stateTextView.visibility = View.VISIBLE
            if_stateTextView.text = onText
        }else if(!state && offText != null){
            if_stateTextView.visibility = View.VISIBLE
            if_stateTextView.text = offText
        }else{
            if_stateTextView.visibility = View.GONE
        }
    }

    override fun save(){
        StoreHelper.getInstance(context).put(key,checked)
    }
}