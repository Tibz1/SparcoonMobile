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
import fr.thibaud.sparcoon.adapter.VehiculeAdapter
import fr.thibaud.sparcoon.adapter.VehiculeItemDecoration

class HomeFragment(
    private val context: MainActivity
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //Créer une liste qui va stocker les véhicules
        val vehiculeList = arrayListOf<VehiculeModel>()

        //Enregistrer un premier véhicule dans la liste (208)
        vehiculeList.add(
            VehiculeModel(
                "308",
                "WW-000-WW",
                "Peugeot 308 1.6 BlueHDi 110 GTLine",
                2018,
                "Diesel",
                3,
                "https://sf2.auto-moto.com/wp-content/uploads/sites/9/2018/07/peugeot_308_gt_line_47-1.jpeg",
                true

            ))

        //Enregistrer un deuxième véhicule dans la liste (zoe)
        vehiculeList.add(
            VehiculeModel(
                "Zoe",
                "WW-001-WW",
                "Renault Zoe R110",
                2018,
                "Électrique",
                1,
                "https://sf1.autoplus.fr/wp-content/uploads/autoplus/2018/04/renault-zoe-les-prix-moteur-r110-devoiles.jpg",
                false
            ))

        //Enregistrer un troisième véhicule dans la liste (up)
        vehiculeList.add(
            VehiculeModel(
                "VW Up",
                "WW-002-WW",
                "Volkswagen Up 1.0 115 GTI",
                2020,
                "Essence",
                1,
                "https://www.frenchdriver.fr/media/upload/2020/05/photo-essai-vw-up-gti-2020-1-001.jpg",
                false
            ))

        //Enregistrer un quatrième véhicule dans la liste (a1)
        vehiculeList.add(
            VehiculeModel(
                "A1",
            "WW-003-WW",
                "Audi A1 1.6 TDI 105 SLine",
                2021,
                "Diesel",
                2,
                "https://www.pfmotors.fr/wp-content/uploads/2018/09/DSC_0005-11.jpg",
                true

            )
        )

        // Récupérer le premier recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        horizontalRecyclerView.adapter = VehiculeAdapter(context, vehiculeList, R.layout.item_horizontal_vehicule)

        // Récupérer le second recyclerview
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = VehiculeAdapter(context, vehiculeList, R.layout.item_vertical_vehicule)
        verticalRecyclerView.addItemDecoration(VehiculeItemDecoration())

        return view
    }
}