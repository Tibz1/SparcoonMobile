package fr.thibaud.sparcoon.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.thibaud.sparcoon.MainActivity
import fr.thibaud.sparcoon.R
import fr.thibaud.sparcoon.VehiculeModel
import fr.thibaud.sparcoon.VehiculeRepository
import fr.thibaud.sparcoon.VehiculeRepository.Singleton.downloadUri
import java.util.*

class AddVehiculeFragment(
    private val context: MainActivity
): Fragment() {

    private var file: Uri? = null
    private var uploadedImage: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_vehicule, container, false)

        //Récupérer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.vehicule_add_page_picture_preview)

        //Récupérer le bouton pour charger l'image
        val pickupImageButton = view.findViewById<Button>(R.id.vehicule_add_page_picture_button)

        //Ouvrir les images du téléphone au click sur le bouton
        pickupImageButton.setOnClickListener { pickupImage() }

        //Récupérer le bouton confirmer
        val confirmButton = view.findViewById<Button>(R.id.vehicule_add_page_add_button)
        confirmButton.setOnClickListener { sendForm(view) }

        return view
    }

    private fun sendForm(view: View) {
        val repo = VehiculeRepository()
        repo.uploadImage(file!!){
            val vehiculeName = view.findViewById<EditText>(R.id.vehicule_add_page_name_input).text.toString()
            val vehiculeImmat = view.findViewById<EditText>(R.id.vehicule_add_page_immat_input).text.toString()
            val vehiculeModel = view.findViewById<EditText>(R.id.vehicule_add_page_model_input).text.toString()
            val vehiculeAnnee = view.findViewById<EditText>(R.id.vehicule_add_page_year_input).text.toString()
            val vehiculeEnergy = view.findViewById<EditText>(R.id.vehicule_add_page_energy_input).text.toString()
            val vehiculeCritair = view.findViewById<Spinner>(R.id.vehicule_add_page_critair_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            //Créer un nouvel objet VehiculeModel
            val vehicule = VehiculeModel(
                UUID.randomUUID().toString(),
                vehiculeName,
                vehiculeImmat,
                vehiculeModel,
                vehiculeAnnee,
                vehiculeEnergy,
                vehiculeCritair,
                downloadImageUrl.toString()
            )

            //Envoyer en bdd
            repo.insertVehicule(vehicule)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){

            //Vérifier si les donnnées sont nulle
            if(data == null || data.data == null) return

            //Récupérer l'image
            file = data.data

            //Mettre à jour l'aperçu de l'image
            uploadedImage?.setImageURI(file)
        }
    }

}