package com.example.owner.nearbyplacesmvvm.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.owner.nearbyplacekotlin.model.NearbyPlaceInfo
import com.example.owner.nearbyplacesmvvm.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.*

class NearbyPlaceAdapter (val items: List<NearbyPlaceInfo>, val clickListener: (NearbyPlaceInfo) -> Unit) :
        RecyclerView.Adapter<NearbyPlaceAdapter.NearbyPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyPlaceViewHolder {
        return NearbyPlaceViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: NearbyPlaceViewHolder, position: Int) = holder.bind(items[position],clickListener)


    class NearbyPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: NearbyPlaceInfo, listener: (NearbyPlaceInfo) -> Unit) = with(itemView) {
            place_name.text = item.name
            Picasso.get().load(item.icon)
                    .placeholder(R.drawable.ics_placeholder)
                    .error(R.drawable.ics_error)
                    .into(icon)
            setOnClickListener {
                listener(item)

            }
        }
    }
}