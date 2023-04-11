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

        var allcities = placesfrag.viewModel.loadcities(placesfrag.requireContext())

        holder.placeName.text = allcities[position]

        holder.itemView.setOnClickListener {

            placesfrag.viewModel.setCurrentCity(placesfrag.requireContext(), allcities[position])

            placesfrag.clickPlace()
        }
    }

    override fun getItemCount(): Int {
        return placesfrag.viewModel.loadcities(placesfrag.requireContext()).size
    }

}