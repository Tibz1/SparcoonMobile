package fr.thibaud.sparcoon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.thibaud.sparcoon.MainActivity
import fr.thibaud.sparcoon.R
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.vehiculeList
import fr.thibaud.sparcoon.adapter.VehiculeAdapter
import fr.thibaud.sparcoon.adapter.VehiculeItemDecoration

class VehiculeListFragment(
    private val context: MainActivity
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicule_list, container, false)

        //récupérer la recyclerview
        val listRecyclerView = view.findViewById<RecyclerView>(R.id.vehicule_list_recycler)
        listRecyclerView.adapter = VehiculeAdapter(context, vehiculeList, R.layout.item_vertical_vehicule)
        listRecyclerView.layoutManager = LinearLayoutManager(context)
        listRecyclerView.addItemDecoration(VehiculeItemDecoration())

        return view
    }

}