package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.LinearLayout
import org.jetbrains.anko.padding
import org.jetbrains.anko.px2dip

abstract class InputField : LinearLayout{

    var key : String = ""
    var title : String = ""

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}

    private fun init(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs,R.styleable.InputField)
        key = ta.getString(R.styleable.InputField_if_key)?:""
        title = ta.getString(R.styleable.InputField_if_title)?:""
        ta.recycle()

        orientation = VERTICAL
        padding = px2dip(1).toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isClickable = true
            foreground = resources.getDrawable(R.drawable.fg_field_ripple,null)
        }
        background = resources.getDrawable(R.drawable.bg_input_field,null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST){
            val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
            super.onMeasure(MeasureSpec.makeMeasureSpec(availableWidth,MeasureSpec.EXACTLY), heightMeasureSpec)
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

    abstract fun save()
}