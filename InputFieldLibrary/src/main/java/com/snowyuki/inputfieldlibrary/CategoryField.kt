package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.field_category.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip

class CategoryField : LinearLayout {

    var title : String
        set(value) {
            if_titleTextView.setText(value)
        }
        get() = if_titleTextView.text.toString()

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}


    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_category,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.CategoryField)
            if_titleTextView.text = ta.getString(R.styleable.CategoryField_if_title)?:""
            ta.recycle()
        }

        val paddingWidth = dip(4)
        backgroundColor = ContextCompat.getColor(context,R.color.input_field_lightGray)
        setPadding(paddingWidth,paddingWidth,paddingWidth,0)
        orientation = VERTICAL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST){
            val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
            super.onMeasure(MeasureSpec.makeMeasureSpec(availableWidth,MeasureSpec.EXACTLY), heightMeasureSpec)
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}