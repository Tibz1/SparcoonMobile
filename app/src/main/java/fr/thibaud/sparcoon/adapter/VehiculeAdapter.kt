package fr.thibaud.sparcoon.adapter

import android.view.LayoutInflater
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.thibaud.sparcoon.R

class VehiculeAdapter(private val layoutId: Int) : RecyclerView.Adapter<VehiculeAdapter.ViewHolder>() {

    //Boite pour ranger tous les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vehiculeImage = view.findViewById<ImageView>(R.id.image_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 5
    }
}