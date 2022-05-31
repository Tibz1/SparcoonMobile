package fr.thibaud.sparcoon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.thibaud.sparcoon.MainActivity
import fr.thibaud.sparcoon.R
import fr.thibaud.sparcoon.VehiculeModel
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.vehiculeList
import fr.thibaud.sparcoon.adapter.VehiculeAdapter
import fr.thibaud.sparcoon.adapter.VehiculeItemDecoration

class HomeFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Récupérer le premier recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = VehiculeAdapter(context, vehiculeList.filter { it.liked }, R.layout.item_horizontal_vehicule)

        // Récupérer le second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = VehiculeAdapter(context, vehiculeList, R.layout.item_vertical_vehicule)
        verticalRecyclerView.addItemDecoration(VehiculeItemDecoration())

        return view
    }
}