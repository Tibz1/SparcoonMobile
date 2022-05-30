package fr.thibaud.sparcoon

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.thibaud.sparcoon.adapter.VehiculeAdapter

class VehiculePopup(
    private val adapter: VehiculeAdapter,
    private val currentVehicule: VehiculeModel
    )  : Dialog(adapter.context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.fragment_vehicule_detail)
        setupComponent()
        setupCloseButton()
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.detail_vehicule_close_button).setOnClickListener{
            //Fermer la fenêtre
            dismiss()
        }
    }

    private fun setupComponent() {
        //Actualiser l'image du véhicule
        val vehiculeImage = findViewById<ImageView>(R.id.detail_vehicule_image)
        Glide.with(adapter.context).load(Uri.parse(currentVehicule.imageUrl)).into(vehiculeImage)

        //Actualiser le nom du véhicule
        findViewById<TextView>(R.id.detail_vehicule_name).text = currentVehicule.name

        //Actualiser l'immatriculation du véhicule
        findViewById<TextView>(R.id.detail_vehicule_immat).text = currentVehicule.immat

        //Actualiser le modèle du véhicule
        findViewById<TextView>(R.id.detail_vehicule_model).text = currentVehicule.modele

        //Actualiser l'année du véhicule
        findViewById<TextView>(R.id.detail_vehicule_year).text = currentVehicule.annee.toString()

        //Actualiser l'énergie du véhicule
        findViewById<TextView>(R.id.detail_vehicule_energy).text = currentVehicule.energie

        //Actualiser le Crit'air du véhicule
        findViewById<TextView>(R.id.detail_vehicule_critair).text = currentVehicule.critair.toString()

    }

}