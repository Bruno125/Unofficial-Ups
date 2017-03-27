package com.brunoaybar.unofficialupc.modules.reserve.options

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.modules.reserve.DisplayableReserveOption

class ReserveOptionsAdapter(val context: Context,
                            var options: List<DisplayableReserveOption> = arrayListOf()): RecyclerView.Adapter<ReserveOptionsAdapter.ViewHolder>() {

    fun update(newOptions: List<DisplayableReserveOption>){
        options = newOptions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_reserve_option,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val option = options[position]
        holder?.tviResourceName?.text = option.name
        holder?.iviSelected?.visibility = if (option.isSelected) View.VISIBLE else View.INVISIBLE
        holder?.containerView?.setOnClickListener { selectionListener?.selected(position) }
    }

    override fun getItemCount(): Int {
        return options.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init { ButterKnife.bind(this,itemView) }

        @BindView(R.id.tviResourceName) lateinit var tviResourceName: TextView
        @BindView(R.id.iviSelected) lateinit var iviSelected: ImageView
        @BindView(R.id.containerView) lateinit var containerView: View
    }

    interface Callback {
        fun selected(position: Int)
    }
    var selectionListener: Callback? = null

}