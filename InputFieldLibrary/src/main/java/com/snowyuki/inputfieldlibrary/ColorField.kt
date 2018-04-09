package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.snowyuki.inputfieldlibrary.storage.StoreHelper
import kotlinx.android.synthetic.main.field_color.view.*
import snowyuki.colorpanellibrary.view.ColorPanelDialog
import snowyuki.colorpanellibrary.view.OnColorSelectListener

class ColorField : InputField {

    var color = 0
    private var fixAlpha = false

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}

    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_color,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.ColorField)
            if_nameTextView.text = title
            color = ta.getColor(R.styleable.ColorField_if_color,0)
            color = StoreHelper.getInstance(context).getInt(key,color)
            colorBlock.changeColor(color)
            if_descTextView.text = ta.getString(R.styleable.ColorField_if_description)?:""
            fixAlpha = ta.getBoolean(R.styleable.ColorField_if_fixedAlpha,false)
            ta.recycle()
        }
        setOnClickListener {
            val dialog = ColorPanelDialog(context)
                    .setColor(color)
                    .setOnColorSelectedListener(object : OnColorSelectListener{
                        override fun onColorSelect(color: Int) {
                            this@ColorField.color = color
                            colorBlock.changeColor(color)
                        }
                    })
            if(fixAlpha)dialog.fixColorAlpha(0xff)
            dialog.show()
        }
    }

    override fun save() {
        StoreHelper.getInstance(context).put(key,colorBlock.getColor())
    }
}