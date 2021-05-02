package com.brandovidal.tragosapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brandovidal.tragosapp.R
import com.brandovidal.tragosapp.base.BaseViewHolder
import com.brandovidal.tragosapp.data.model.Drink
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.drinks_row.view.*

class MainAdapter(
    private val context: Context,
    private val drinkList: MutableList<Drink>,
    private val itemClickListener: OnDrinkClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnDrinkClickListener {
        fun onDrinkClick(drink: Drink, position: Int)
    }


    fun deleteDrink(position: Int){
        drinkList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.drinks_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(drinkList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<Drink>(itemView) {
        override fun bind(item: Drink, position: Int) {
            Glide.with(context).load(item.image).centerCrop().into(itemView.img_drink)
            itemView.txt_title.text = item.name
            itemView.txt_description.text = item.description

            itemView.setOnClickListener { itemClickListener.onDrinkClick(item, position) }
        }
    }
}