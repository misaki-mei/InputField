package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.snowyuki.inputfieldlibrary.storage.StoreHelper
import com.warkiz.widget.IndicatorSeekBar
import kotlinx.android.synthetic.main.field_seekbar.view.*

class SeekBarField : InputField {

    var progress = 0f

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}

    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_seekbar,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.SeekBarField)
            if_nameTextView.text = title
            if_seekBar.min = ta.getFloat(R.styleable.SeekBarField_if_minProgress,0f)
            if_seekBar.max = ta.getFloat(R.styleable.SeekBarField_if_maxProgress,100f)
            progress = ta.getFloat(R.styleable.SeekBarField_if_progress,0f)
            progress = StoreHelper.getInstance(context).getFloat(key,progress)
            if_seekBar.setProgress(progress)
            progressIndicator.text = progress.toInt().toString()
            if_seekBar.setOnSeekChangeListener(object : IndicatorSeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: IndicatorSeekBar?, progress: Int, progressFloat: Float, fromUserTouch: Boolean) = Unit
                override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?, thumbPosOnTick: Int) = Unit
                override fun onSectionChanged(seekBar: IndicatorSeekBar?, thumbPosOnTick: Int, textBelowTick: String?, fromUserTouch: Boolean) = Unit
                override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?){
                    progress = if_seekBar.progressFloat
                    progressIndicator.text = progress.toInt().toString()
                }
            })
            ta.recycle()
        }
    }

    override fun save() {
        StoreHelper.getInstance(context).put(key,progress)
    }
}