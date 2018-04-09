package com.snowyuki.inputfieldlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.snowyuki.inputfieldlibrary.dialog.MultiSelectDialog
import com.snowyuki.inputfieldlibrary.storage.FieldData
import com.snowyuki.inputfieldlibrary.storage.withFieldDB
import kotlinx.android.synthetic.main.field_single_select.view.*
import org.jetbrains.anko.doAsync

class MultiSelectField : InputField {

    private lateinit var selections : Array<Boolean>

    var selectionTexts = List(0,{""})
        get() = nameArray.filterIndexed { index, _ -> selections[index] }

    private var selectionData = List(0,{FieldData()})
        get() = selectionTexts.map { FieldData(0,key,it) }

    private lateinit var nameArray : Array<String>
    private var descArray : Array<String>? = null
    private lateinit var valueArray : Array<String>

    var minSelections = -1
    var maxSelections = -1

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init(attrs)}

    private fun init(attrs: AttributeSet?){
        View.inflate(context,R.layout.field_multi_select,this)

        if(attrs != null){
            val ta = context.obtainStyledAttributes(attrs,R.styleable.MultiSelectField)
            if_nameTextView.text = title
            val nameArrayId = ta.getResourceId(R.styleable.MultiSelectField_if_entries,-1)
            if(nameArrayId != -1){
                nameArray = resources.getStringArray(nameArrayId)
            }else{nameArray = Array(0,{""}) }
            val valueArrayId = ta.getResourceId(R.styleable.MultiSelectField_if_entryValues,-1)
            if(valueArrayId != -1){
                valueArray = resources.getStringArray(valueArrayId)
            }else{valueArray = Array(0,{""})}
            val descArrayId = ta.getResourceId(R.styleable.MultiSelectField_if_entryDesc,-1)
            if(descArrayId != -1){
                descArray = resources.getStringArray(descArrayId)
            }
            minSelections = ta.getInt(R.styleable.MultiSelectField_if_minSelections,-1)
            maxSelections = ta.getInt(R.styleable.MultiSelectField_if_maxSelections,-1)

            selections = Array(valueArray.size,{false})
            context.withFieldDB {fieldDataDB ->
                doAsync {
                    val selectionTexts = fieldDataDB.fieldDataDao().getValues(key)
                    valueArray.forEachIndexed { index, value ->
                        if(selectionTexts.contains(value)){
                            selections[index] = true
                        }
                    }
                }
            }
            ta.recycle()
        }

        setOnClickListener {
            MultiSelectDialog(context,nameArray,selections)
                    .setDescArray(descArray)
                    .setSelectionLimit(minSelections,maxSelections)
                    .setOnSelectListener {resultArray ->
                        selections = resultArray
                    }
                    .show()
        }

    }

    fun setData(nameArr : Array<String>,valueArr : Array<String>,descArr : Array<String>? = null){
        this.nameArray = nameArr
        this.valueArray = valueArr
        this.descArray = descArr
    }

    fun setSelection(selections : Array<Boolean>){
        this.selections = selections
    }

    override fun save() {
        context.withFieldDB {fieldDataDB ->
            val fieldDataDao = fieldDataDB.fieldDataDao()
            fieldDataDao.deleteData(key)
            fieldDataDao.insertData(selectionData)
        }
    }
}