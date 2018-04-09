package com.snowyuki.inputfieldlibrary.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.snowyuki.inputfieldlibrary.R
import org.jetbrains.anko.find

class MultiSelectListAdapter(private val c : Context,
                             private val nameArray : Array<String>,
                             private val descArray : Array<String>?,
                             val selections : Array<Boolean>) : RecyclerView.Adapter<MultiSelectListAdapter.SelectItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectItemViewHolder {
        return SelectItemViewHolder(LayoutInflater.from(c).inflate(R.layout.field_list_item,parent,false))
    }

    override fun getItemCount() = nameArray.size

    override fun onBindViewHolder(holder: SelectItemViewHolder, position: Int) {
        holder.nameTextView.text = nameArray[position]
        if(descArray != null){
            holder.descTextView.text = descArray[position]
        }
        holder.checkBox.isChecked = selections[position]
    }

    inner class SelectItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val nameTextView =  view.find<TextView>(R.id.nameTextView)
        val descTextView = view.find<TextView>(R.id.descTextView)
        val checkBox = view.find<CheckBox>(R.id.checkBox)
        init {
            view.setOnClickListener {
                selections[adapterPosition] = !selections[adapterPosition]
                this@MultiSelectListAdapter.notifyDataSetChanged()
            }
        }
    }
}