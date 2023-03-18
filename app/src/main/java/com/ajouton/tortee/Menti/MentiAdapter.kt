package com.ajouton.tortee.Menti

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajouton.tortee.MainActivity
import com.ajouton.tortee.databinding.ItemMentiBinding

class MentiAdapter(private val context: MainActivity, private val dataList: ArrayList<Menti_thumnail>) :
    RecyclerView.Adapter<MentiAdapter.ViewHolder>(){
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner  class ViewHolder(private var binding: ItemMentiBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Menti_thumnail){
            binding.mentiName.text=item.name
            binding.mentiTvDate.text=item.date.toString()
            binding.mentiTvTitle.text=item.title

        }



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=  ItemMentiBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])

    }

    override fun getItemCount(): Int {

        return dataList.size
    }
}