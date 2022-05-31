package fr.thibaud.sparcoon

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
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
        setupDeleteButton()
        setupStarButton()
    }

    private fun updateStar(button: ImageView){
        //Méthode premettant de mettre à jour le statut du bouton like
        if (currentVehicule.liked){
            button.setImageResource(R.drawable.ic_star)
        } else {
            button.setImageResource(R.drawable.ic_unstar)
        }
    }

    private fun setupStarButton() {
        //Récupérer la star
        val starButton = findViewById<ImageView>(R.id.detail_vehicule_star_button)

        //Afficher la bonne star
        updateStar(starButton)

        //Interaction sur le bouton
        starButton.setOnClickListener{
            currentVehicule.liked = !currentVehicule.liked
            val repo = VehiculeRepository()
            repo.updateVehicule(currentVehicule)
            updateStar(starButton)
        }

    }

    private fun setupDeleteButton() {
        findViewById<Button>(R.id.detail_vehicule_delete_button).setOnClickListener {
            //Supprimer le véhicule de la bdd
            val repo = VehiculeRepository()
            repo.deleteVehicule(currentVehicule)
            dismiss()
        }
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
        findViewById<TextView>(R.id.detail_vehicule_year).text = currentVehicule.annee

        //Actualiser l'énergie du véhicule
        findViewById<TextView>(R.id.detail_vehicule_energy).text = currentVehicule.energie

        //Actualiser le Crit'air du véhicule
        findViewById<TextView>(R.id.detail_vehicule_critair).text = currentVehicule.critair

    }

}