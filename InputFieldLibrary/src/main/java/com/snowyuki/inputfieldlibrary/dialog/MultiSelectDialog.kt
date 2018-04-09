package com.snowyuki.inputfieldlibrary.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.snowyuki.inputfieldlibrary.R
import com.snowyuki.inputfieldlibrary.adapter.MultiSelectListAdapter
import kotlinx.android.synthetic.main.field_dialog_btn.*
import kotlinx.android.synthetic.main.field_dialog_multi_select.*

class MultiSelectDialog(private val c : Context,
                        private val nameArray : Array<String>,
                        private val selections : Array<Boolean>) : AlertDialog(c){

    private var descArray : Array<String>? = null
    private var maxSelection = -1
    private var minSelection = -1
    private var onSelect : (Array<Boolean>) -> Unit = {}

    private lateinit var listAdapter: MultiSelectListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.field_dialog_multi_select)
        initUI()
    }

    private fun initUI() {
        listAdapter = MultiSelectListAdapter(c,nameArray,descArray,selections)
        recyclerView.adapter = listAdapter
        selectAllTextView.setOnClickListener {
            selections.forEachIndexed { index, _ -> selections[index] = true }
            listAdapter.notifyDataSetChanged()
        }
        clearTextView.setOnClickListener {
            selections.forEachIndexed { index, _ -> selections[index] = false }
            listAdapter.notifyDataSetChanged()
        }
        if_positiveBtn.setOnClickListener {
            val selectionNum = selections.count { it }
            if(maxSelection > 0 && selectionNum > maxSelection)return@setOnClickListener
            if(minSelection >= 0 && selectionNum < minSelection)return@setOnClickListener
            onSelect(selections)
            this.dismiss()
        }
        if_negativeBtn.setOnClickListener { this.dismiss() }
    }

    fun setOnSelectListener(f : (resultArray : Array<Boolean>)->Unit): MultiSelectDialog {
        onSelect = f
        return this
    }

    fun setSelectionLimit(minSelection : Int,maxSelection : Int): MultiSelectDialog {
        this.minSelection = minSelection
        this.maxSelection = maxSelection
        return this
    }

    fun setDescArray(arr : Array<String>?): MultiSelectDialog {
        this.descArray = arr
        return this
    }
}