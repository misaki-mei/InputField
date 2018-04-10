package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.snowyuki.inputfieldlibrary.adapter.SingleSelectListAdapter
import com.snowyuki.inputfieldlibrary.storage.StoreHelper
import kotlinx.android.synthetic.main.field_single_select.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.find
import org.jetbrains.anko.okButton

class SingleSelectField : InputField {

    var selection = -1
    set(value) {
        field = value
        if_valueTextView.text = if(selection < 0) "" else nameArray[selection]
    }

    var selectionText : String? = null
        get() = if(selection == -1)null
        else valueArray[selection]

    lateinit var nameArray : Array<String>
    var descArray : Array<String>? = null
    lateinit var valueArray : Array<String>

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}

    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_single_select,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.SingleSelectField)
            if_nameTextView.text = title
            val nameArrayId = ta.getResourceId(R.styleable.SingleSelectField_if_entries,-1)
            nameArray = if(nameArrayId != -1){
                resources.getStringArray(nameArrayId)
            }else{
                Array(0,{""})
            }
            val valueArrayId = ta.getResourceId(R.styleable.SingleSelectField_if_entryValues,-1)
            valueArray = if(valueArrayId != -1){
                resources.getStringArray(valueArrayId)
            }else{
                Array(0,{""})
            }
            val descArrayId = ta.getResourceId(R.styleable.SingleSelectField_if_entryDesc,-1)
            if(descArrayId != -1){
                descArray = resources.getStringArray(descArrayId)
            }
            selection = StoreHelper.getInstance(context).getInt(key,-1)
            if(selection != -1){
                if_valueTextView.visibility = View.VISIBLE
                if_valueTextView.text = nameArray[selection]
            }
            ta.recycle()
        }

        setOnClickListener {
            context.alert {
                val view = LayoutInflater.from(context).inflate(R.layout.field_dialog_single_select, null)
                customView = view
                val adapter = SingleSelectListAdapter(context,nameArray,descArray)
                adapter.selection = selection
                val listView = view.find<RecyclerView>(R.id.recyclerView)
                listView.adapter = adapter
                okButton {
                    selection = adapter.selection
                    if(selection != -1){
                        if_valueTextView.visibility = View.VISIBLE
                        if_valueTextView.text = nameArray[selection]
                    }
                }
                cancelButton {  }
            }.show()
        }

    }

    fun setData(nameArr : Array<String>,valueArr : Array<String>,descArr : Array<String>? = null){
        this.nameArray = nameArr
        this.valueArray = valueArr
        this.descArray = descArr
    }

    override fun save() {
        StoreHelper.getInstance(context).put(key,selection)
    }
}