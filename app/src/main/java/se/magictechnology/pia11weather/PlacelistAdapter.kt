package se.magictechnology.pia11weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacelistAdapter(var placesfrag : PlacelistFragment) : RecyclerView.Adapter<PlacelistAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val placeName : TextView

        init {
            placeName = view.findViewById(R.id.placeitemPlacename)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placeName.text = "Eslöv"

        holder.itemView.setOnClickListener {
            placesfrag.clickPlace()
        }
    }

    override fun getItemCount(): Int {
        return 500
    }

}