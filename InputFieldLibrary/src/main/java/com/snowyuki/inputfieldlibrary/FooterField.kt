package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.field_footer.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding

class FooterField : LinearLayout {

    var text : String
        set(value) {
            if_textView.text = value
        }
        get() = if_textView.text.toString()

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}


    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_footer,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.FooterField)
            if_textView.text = ta.getString(R.styleable.FooterField_if_text)?:""
            ta.recycle()
        }

        padding = dip(16)
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